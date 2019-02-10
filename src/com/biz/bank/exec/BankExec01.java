package com.biz.bank.exec;

import com.biz.bank.service.*;

public class BankExec01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String file = "src/com/biz/bank/bankBalance.txt";
		BankService bs = new BankService(file);
		bs.readBalance();
		bs.bankMenu();
		
		bs.bankInput();
		
	}
	
}
