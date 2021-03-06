package com.hangum.db.browser.rap.core.dialog.dbconnect;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

import com.hangum.db.browser.rap.core.Messages;
import com.hangum.db.browser.rap.core.viewers.connections.ManagerViewer;
import com.hangum.db.commons.sql.TadpoleSQLManager;
import com.hangum.db.commons.sql.define.DBDefine;
import com.hangum.db.dao.system.UserDBDAO;
import com.hangum.db.util.PingTest;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 로그인시에 사용하게될 디비의 abstrace composite
 * 
 * @author hangum
 *
 */
public abstract class AbstractLoginComposite extends Group {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3434604591881525231L;
	private static final Logger logger = Logger.getLogger(AbstractLoginComposite.class);
	protected UserDBDAO userDB;
	protected DBDefine selectDB;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AbstractLoginComposite(DBDefine dbDefine, Composite parent, int style) {
		super(parent, style);
		this.selectDB = dbDefine;
		crateComposite();
	}

	/**
	 * 
	 * @param parent
	 * @return
	 */
	protected abstract void crateComposite();
	
	/**
	 * 초기데이터 로드 및 처리 
	 */
	protected abstract void init();
	
	/**
	 * DB 연결
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean connection();

	/**
	 * DB 가 정상 연결되었을때 객체
	 * 
	 * @return
	 */
	protected UserDBDAO getDBDTO() {
		return userDB;
	}
	
	/**
	 * host, port에 ping
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public boolean isPing(String host, String port) throws NumberFormatException {
		
		
		int stats = PingTest.ping(host, Integer.parseInt(port), 1000);
		if(PingTest.SUCCESS == stats) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * db 연결정보가 올바른지 검증합니다.
	 * 
	 * @param loginInfo
	 * @param searchTable 디비의 테이블 검증을위한 쿼리 
	 * @return
	 */
	public boolean connectValite(UserDBDAO loginInfo, String searchTable) {
		// 이미 연결한 것인지 검사한다.
		final ManagerViewer managerView = (ManagerViewer)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ManagerViewer.ID);
		if( !managerView.isAdd(DBDefine.MYSQL_DEFAULT, loginInfo) ) {
			MessageDialog.openError(null, Messages.DBLoginDialog_23, Messages.DBLoginDialog_24);
			
			return false;
		}

		if(DBDefine.getDBDefine(loginInfo.getType()) != DBDefine.MONGODB_DEFAULT) {
			// db가 정상적인지 채크해본다 
			try {
				SqlMapClient sqlClient = TadpoleSQLManager.getInstance(loginInfo);
				List showTables = sqlClient.queryForList("tableList", searchTable);
				
			} catch (Exception e) {
				logger.error("MySQL DB Connecting... ", e);
				MessageDialog.openError(null, Messages.DBLoginDialog_26, Messages.DBLoginDialog_27 + "\n" + e.getMessage());
				
				return false;
			}
		}
		
		return true;
	}

}
