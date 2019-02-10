package com.biz.bank.service;

import java.io.*;
import java.text.*;
import java.time.*;
import java.util.*;

import com.biz.bank.vo.*;

public class BankService {
	List<BankVO> bankList;
	String balFile;
	Scanner scan;
	
	public BankService(String balFile) {
		bankList = new ArrayList<>();
		this.balFile = balFile;
		scan = new Scanner(System.in);
	}
	public void bankIOWrite(BankVO vo) {
		
		String gNum = "src/com/biz/bank/iolist/"+vo.getStrID()+".txt";
		
		FileWriter fw;
		PrintWriter pw;
		
		try {
			fw = new FileWriter(gNum,true);
			pw = new PrintWriter(fw);
			
			if(vo.getStrIO().equals("입금")) {
				pw.println(
					vo.getStrID()+":"
					+vo.getStrLastDate()+":"
					+vo.getStrIO()+":"
					+vo.getIntIOCash()+":"
					+"0"+":"
					+vo.getIntBalance()+":");
			}
			
			if(vo.getStrIO().equals("출금")) {
				pw.println(
					vo.getStrID()+":"
					+vo.getStrLastDate()+":"
					+vo.getStrIO()+":"
					+"0"+":"
					+vo.getIntIOCash()+":"
					+vo.getIntBalance()+":");
			}
			pw.close();
			fw.close();
					
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void bankOutput() {
		System.out.println("출금계좌 입력 >>");
		String strId = scan.nextLine();
		BankVO vo = bankFindId(strId);
		if(vo == null) {
			System.out.println("계좌번호 오류");
			return;
		}
		//계좌번호가 정상이고, vo에는 
		//해당 계좌번호의 정보가 담겨있다.
		
		System.out.print("출금액 >>>");
		String strO = scan.nextLine();
		int intO = Integer.valueOf(strO);
		if(intO > vo.getIntBalance()) {
			System.out.println("잔액부족");
			return;
		}
		// 새로운 코드
		// 출금일 경우 vo.strIO에 "출금"문자열 저장
		// vo.intIOCash에 출금 금액을 저장하고
		// balance에 + 출금액을 저장한다.
		
		vo.setStrIO("출금");
		vo.setIntIOCash(intO);
	
		vo.setIntBalance(vo.getIntBalance() - intO);
		System.out.println("출금처리완료");
		bankIOWrite(vo);
	}
	public void bankInput() {
		System.out.println("입금계좌 입력 >>");
		String strId = scan.nextLine();
		BankVO vo = bankFindId(strId);
		if(vo == null) {
			System.out.println("계좌번호 오류");
			return;
		}
		//계좌번호가 정상이고, vo에는 
		//해당 계좌번호의 정보가 담겨있다.
		
		System.out.print("입금액 >>>");
		String strIO = scan.nextLine();
		int intIO = Integer.valueOf(strIO);
		
		// 새로운 코드
		// 입금일 경우 vo.strIO에 "입금"문자열 저장
		// vo.intIOCash에 입금 금액을 저장하고
		// balance에 + 입금액을 저장한다.
		
		vo.setStrIO("입금");
		vo.setIntIOCash(intIO);
		vo.setIntBalance(vo.getIntBalance() + intIO);
		// old java 코드로 현재날짜 가져오기.
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date();
		String strDate = sm.format(curDate);
		
		// New Java(1.8) 코드로 현재날짜 가져오기
		LocalDate Id = LocalDate.now();
		strDate = Id.toString();
		vo.setStrLastDate(strDate);
		
		System.out.println("입금처리완료");
		bankIOWrite(vo);
	}
	
	/*
	 * 계좌번호를 매개변수로 받아서
	 * bankList에서 계좌를 조회하고
	 * bankList에 계좌가 있으면
	 * 		찾은 vo를 return 하고
	 * 없으면
	 * 		null값을 return 하도록 한다.
	 */
	public BankVO bankFindId(String strId) {
		for(BankVO v: bankList) {
			if(v.getStrID().equals(strId)) {
				System.out.println("계좌번호 있음");
				return v;
			}
		}
		return null;
	}
	public void bankMenu() {
		System.out.println("=============================================");
		System.out.println("1.입금 | 2.출금 | 3.계좌 (0.종료) ");
		System.out.println("=============================================");
		System.out.print("업무선택 >>");
		
		String strselect = scan.nextLine();
		int intselect = Integer.valueOf(strselect);
		if(intselect == 0) System.out.println("good bye");
		if(intselect == 1) this.bankInput();
		if(intselect == 2) this.bankOutput();
		if(intselect == 3) System.out.println("계좌조회");
		
			
	}
	
	public void readBalance() {
		FileReader fr;
		BufferedReader buffer;
		
		
			try {
				fr = new FileReader(balFile);
				buffer = new BufferedReader(fr);
				while(true) {
					String line = buffer.readLine();
					if(line == null) break;
					String[] lines = line.split(":");
					
					BankVO vo = new BankVO();
					
					String strline = lines[1];
					int intlines = Integer.valueOf(strline);
					
					vo.setStrID(lines[0]);
					vo.setIntBalance(intlines);
					vo.setStrLastDate(lines[2]);
					
					bankList.add(vo);
					
				}
				buffer.close();
				fr.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


