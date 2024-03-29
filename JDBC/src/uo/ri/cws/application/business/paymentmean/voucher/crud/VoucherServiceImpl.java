package uo.ri.cws.application.business.paymentmean.voucher.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherService;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherSummaryDto;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class VoucherServiceImpl implements VoucherService {

	private  CommandExecutor exectutor = new CommandExecutor();
	@Override
	public int generateVouchers() throws BusinessException {
		return exectutor.execute(new AddVoucher());
	}

	@Override
	public Optional<VoucherDto> findVouchersById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VoucherDto> findVouchersByClientId(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VoucherSummaryDto> getVoucherSummary() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
