package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vo.PersonVo;


@Controller // servlet-context에서 만들어줌
public class ParamTestController {
	
	// DS에게 RequestMapping에 따른 메소드 호출(invoke) 시 request 정보를 넣어달라고 요청
	// ParamTestController가 auto detecting(자동 생성) 시에만 Autowired 실행됨 => 이에 관한 설정은 servlet-context에서 함
	// 단 수동 생성 시에는 <context:annotation-config/> <- 이 코드를 수동 생성 코드 윗쪽에 작성해야 함
	// ip 가져올 때 사용
	@Autowired		// 자동 연결 해줘
	HttpServletRequest request;
	
	// 세션 필요할 때 사용 -> ex) 로그인..
	@Autowired
	HttpSession session;
	
	// 파일업로드 기능 시 사용
	@Autowired
	ServletContext application;
	
	// 생성자
	public ParamTestController() {
		// TODO Auto-generated constructor stub
		System.out.println("--- ParamTestController() ---");
	}
	
	// 파라미터 낱개로 받기     
	//  /insert1.do?name=홍길동&age=20&tel=010-111-1234 -> 이렇게 요청 받음.  name=홍길동&age=20&tel=010-111-1234 <- 이게 parameter
	@RequestMapping("/insert1.do")
						  // 파라미터 받기() 괄호 안에 적으면 됨                
	public String insert1(@RequestParam(name="name") String irum,	// @RequestParam(name=" ") parameter명과 수신 될 변수명이 다를 경우 처리하는 방법(보통 이름 맞춰서 사용하긴 함)
						  int age,									// @RequestParam(name=" ") 생략 시 parameter명과 동일한 변수명에 값 넣어줌
						  String tel,
						  @RequestParam(name="nation",required = false, defaultValue = "한국") String nation,   
						  Model model) {							 // required = false : 선택사항. 미입력시 기본 true(필수입력)	
																	 // defaultValue = "한국" 값이 없다면 기본값 "한국"으로 줘라
		// 메소드 인자 : DispacherServlete에 대한 요구사항
		
		String ip = request.getRemoteAddr();
		System.out.println("요청자 ip : " + ip);
		
		// Model 통한 request binding
		model.addAttribute("name", irum);
		model.addAttribute("age", age);
		model.addAttribute("tel", tel);
		model.addAttribute("nation", nation);
		
		return "result1";   // views 폴더 밑에 result1.jsp 만들기
	}
	
	
	// 파라미터 객체로 받기
	//  /insert2.do?name=홍길동&age=20&tel=010-111-1234
	@RequestMapping("/insert2.do")
	public String insert2(PersonVo vo, Model model) {
		
		// Vo로 파라미터 넘길 시
		// 각 파라미터 받기 -> Vo 생성 후 값 넣어주고 전달
		// 메소드 인자 : DispacherServlete에 대한 요구사항
		// request binding
		model.addAttribute("vo", vo);
		
		return "result2";  // views 폴더 안에 result2.jsp 만들기
	}
	
	
	// 파라미터 Map으로 받기
	//  /insert3.do?name=홍길동&age=20&tel=010-111-1234
	@RequestMapping("/insert3.do")
					   // 요청된 파라미터 기준으로 Map 만들어줘   
	public String insert3(@RequestParam Map map, Model model, HttpServletRequest request) {
															  // HttpServletRequest request -> 호출하는 객체(DS)한테 달라고 함(ip 쓰기 위해 하는 작업)
		String ip = request.getRemoteAddr();
		
		System.out.println("요청자 ip : " + ip);
		
		model.addAttribute("map", map);
		model.addAttribute("ip", ip);
		
		return "result3";  // views 폴더 안에 result3.jsp 만들기
	}
	
	
	
	
	
	
	
	
	
	
	
}