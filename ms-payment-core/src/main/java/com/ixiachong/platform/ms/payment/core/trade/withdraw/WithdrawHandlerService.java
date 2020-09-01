/*
 * Project: Payment
 * Document: HandlerService
 * Date: 2020/8/14 11:51 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.response.MerchantResponse;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import lombok.extern.java.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Level;

@Log
@Service
public class WithdrawHandlerService extends AbstractService implements TradeHandler<BizContent, Result> {
    @Resource(name = "executorService")
    ExecutorService executorService;
    @Value("${application.future.timeout:1}")
    private Integer timeout;

    @Override
    public Result handle(TradeRequest<BizContent> request) throws TradeException {
        BizContent bizContent = checkBizContent(request);

        Optional<Withdraw> byRequestId = withdrawDao.findByRequestId(request.getRequestId());
        if (byRequestId.isPresent()) {
            return toResult(byRequestId.get());
        }
        Optional<Withdraw> optional = withdrawDao.findByOutTradeNoAndAppId(bizContent.getOutTradeNo(), request.getAppId());
        if (optional.isPresent()) {
            Errors.OUT_TRADE_NO_EXISTS.throwException(TradeException.class);
        }

        Withdraw withdraw = saveRequest(request);// 登记交易信息
        Callable<Result> callable = () -> new Processor(withdraw, request,
                Context.builder()
                        .beanFinder(beanFinder)
                        .merchantService(merchantService)
                        .transactionService(transactionService)
                        .idGenerator(this::newId)
                        .noGenerator(() -> this.newId().replaceAll("-", ""))
                        .tradeFinder(withdrawDao::findByRequestId)
                        .allowWithdraw(no -> {
                            try {
                                MerchantResponse response = merchantService.getOne(no);
                                return response != null && CollectionUtils.isNotEmpty(response.getServices())
                                        && response.getServices().contains(Const.TRADE_WITHDRAW);
                            } catch (Exception ex) {
                                log.log(Level.WARNING, ex.getMessage(), ex);
                                return false;
                            }
                        })
                        .saveWithdrawFreezeRequest(freezeRequestDao::save)
                        .saveWithdrawFreezeResponse(freezeResponseDao::save)
                        .saveWithdrawChannelRequest(channelRequestDao::save)
                        .saveWithdrawChannelResponse(channelResponseDao::save)
                        .saveWithdrawConfirmRequest(confirmRequestDao::save)
                        .saveWithdrawConfirmResponse(confirmResponseDao::save)
                        .applicationMerchant(applicationWithdrawMerchantDao::getMerchantNoByAppIdAAndChannel)
                        .merchantFinder(merchantService::getOne)
                        .merchantChannelParameters(merchantService::getChannel)
                        .saveWithdraw(withdrawDao::save)
                        .build()).process();
        Future<Result> future = executorService.submit(callable);
        try {
            return future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return toResult(withdraw);
        }
    }

    private Withdraw saveRequest(TradeRequest<BizContent> request) {
        Withdraw withdraw = BeanUtils.mapping(request, Withdraw.class);
        org.springframework.beans.BeanUtils.copyProperties(request.getBizContent(), withdraw);
        withdraw.setAccountingTime(new Date());
        withdraw.setTradeNo(newId().replaceAll("-", ""));
        withdraw.setFee(BigDecimal.ZERO);
        withdraw.setStatus(StatusType.PROCESSING);
        return withdrawDao.save(setCreatedEntityProperties(withdraw));
    }
}
