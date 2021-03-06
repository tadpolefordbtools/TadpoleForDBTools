package com.hangum.db.session.manager;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.eclipse.rwt.RWT;

import com.hangum.db.dao.system.UserInfoDataDAO;

/**
 * tadpole의 session manager입니다
 * 
 * @author hangum
 *
 */
public class SessionManager {
	/**
	 * <pre>
	 * 		MANAGER_SEQ는 그룹의 manager 권한 사용자의 seq 입니다.  seq로  그룹의 db list를 얻기위해 미리 가져옵니다.
	 * 
	 * 
	 * </pre>
	 * 
	 * @author hangum
	 */
	enum SESSEION_NAME {GROUP_SEQ, SEQ, LOGIN_EMAIL, LOGIN_PASSWORD, LOGIN_NAME, LOGIN_TYPE, MANAGER_SEQ, USER_INFO_DATA}
	
	/**
	 * 신규 user의 사용자를 등록
	 * 
	 * @param email
	 * @param name
	 */
	public static void newLogin(int groupSeq, int seq, String email, String password, String name, String userType, int managerSeq) {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		
		sStore.setAttribute(SESSEION_NAME.GROUP_SEQ.toString(), groupSeq);
		sStore.setAttribute(SESSEION_NAME.SEQ.toString(), seq);
		sStore.setAttribute(SESSEION_NAME.LOGIN_EMAIL.toString(), email);
		sStore.setAttribute(SESSEION_NAME.LOGIN_PASSWORD.toString(), password);
		sStore.setAttribute(SESSEION_NAME.LOGIN_NAME.toString(), name);
		sStore.setAttribute(SESSEION_NAME.LOGIN_TYPE.toString(), userType);
		sStore.setAttribute(SESSEION_NAME.MANAGER_SEQ.toString(), managerSeq);
	}
	
	public static int getGroupSeq() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		return (Integer)sStore.getAttribute(SESSEION_NAME.GROUP_SEQ.toString());
	}
	
	public static int getSeq() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		Object obj = sStore.getAttribute(SESSEION_NAME.SEQ.toString());
		
		if(obj == null) return 0;
		else return (Integer)obj;
	}
	
	public static String getEMAIL() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		return (String)sStore.getAttribute(SESSEION_NAME.LOGIN_EMAIL.toString());
	}
	
	public static String getPassword() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		return (String)sStore.getAttribute(SESSEION_NAME.LOGIN_PASSWORD.toString());
	}
	
	public static String getName() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		return (String)sStore.getAttribute(SESSEION_NAME.LOGIN_NAME.toString());
	}
	
	public static String getLoginType() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		return (String)sStore.getAttribute(SESSEION_NAME.LOGIN_TYPE.toString());
	}
	
	public static int getManagerSeq() {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		return (Integer)sStore.getAttribute(SESSEION_NAME.MANAGER_SEQ.toString());
	}

	/**
	 * 초기 접속시 프리퍼런스 정보를 로드합니다.
	 */
	public static void setUserInfos(Map<String, Object> mapUserInfo) {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		sStore.setAttribute(SESSEION_NAME.USER_INFO_DATA.toString(), mapUserInfo);		
	}
	
	/**
	 * 기존 세션 정보를 추가합니다. 
	 * @param key
	 * @param obj
	 */
	public static void setUserInfo(String key, String obj) {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		Map<String, Object> mapUserInfoData = (Map<String, Object>)sStore.getAttribute(SESSEION_NAME.USER_INFO_DATA.toString());
		UserInfoDataDAO userInfoDataDAO = (UserInfoDataDAO)mapUserInfoData.get(key);
		userInfoDataDAO.setValue(obj);
		mapUserInfoData.put(key, userInfoDataDAO);
		
		sStore.setAttribute(SESSEION_NAME.USER_INFO_DATA.toString(), mapUserInfoData);
	}
	
	/**
	 * 사용자 User 정보 .
	 * 
	 * @param key
	 * @return
	 */
	public static UserInfoDataDAO getUserInfo(String key) {
		HttpSession sStore = RWT.getSessionStore().getHttpSession();
		Map<String, Object> mapUserInfoData = (Map<String, Object>)sStore.getAttribute(SESSEION_NAME.USER_INFO_DATA.toString());
		
		return (UserInfoDataDAO)mapUserInfoData.get(key);
	}
	
}
