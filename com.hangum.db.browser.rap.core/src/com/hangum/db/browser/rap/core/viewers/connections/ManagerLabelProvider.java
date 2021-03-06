package com.hangum.db.browser.rap.core.viewers.connections;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.hangum.db.browser.rap.core.Activator;
import com.hangum.db.commons.sql.define.DBDefine;
import com.hangum.db.dao.ManagerListDTO;
import com.hangum.db.dao.system.UserDBDAO;
import com.hangum.db.dao.system.UserDBResourceDAO;
import com.hangum.db.define.Define;
import com.hangum.db.session.manager.SessionManager;
import com.swtdesigner.ResourceManager;

/**
 * manager view label provider
 * 
 * @author hangum
 *
 */
public class ManagerLabelProvider extends LabelProvider {
	
	@Override
	public Image getImage(Object element) {

		if(element instanceof ManagerListDTO) {
			
			ManagerListDTO dto = (ManagerListDTO)element;
			if(DBDefine.MYSQL_DEFAULT == dto.getDbType()) 
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/mysql-add.png"); //$NON-NLS-1$
			else if(DBDefine.ORACLE_DEFAULT == dto.getDbType()) 
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/oracle-add.png"); //$NON-NLS-1$
			else if(DBDefine.SQLite_DEFAULT == dto.getDbType()) 
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/sqlite-add.png"); //$NON-NLS-1$
			else if(DBDefine.MSSQL_DEFAULT == dto.getDbType()) 
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/mssql-add.png"); //$NON-NLS-1$
			else if(DBDefine.CUBRID_DEFAULT == dto.getDbType()) 
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/cubrid-add.png"); //$NON-NLS-1$
			
			else if(DBDefine.MONGODB_DEFAULT == dto.getDbType()) 
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/mongodb-add.png"); //$NON-NLS-1$
			
		} else if(element instanceof UserDBDAO) {
			return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/server_database.png"); //$NON-NLS-1$
		} else if(element instanceof UserDBResourceDAO) {
			UserDBResourceDAO dao = (UserDBResourceDAO)element;
			if(Define.RESOURCE_TYPE.ERD.toString().equals( dao.getType() )) {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/relation.png"); //$NON-NLS-1$
			} else {
				return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "resources/icons/sql-query.png"); //$NON-NLS-1$
			}
		}
		
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if(element instanceof ManagerListDTO) {
			ManagerListDTO dto = (ManagerListDTO)element;
			return dto.getName();
			
		} else if(element instanceof UserDBDAO) {
			UserDBDAO dao = (UserDBDAO)element;
			
			// 자신의 디비만 보이도록 수정
			if(dao.getUser_seq() == SessionManager.getSeq()) {
				return dao.getDisplay_name() + " (" + dao.getUser() + "@" + dao.getDatabase() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			} else {
				return dao.getDisplay_name() + " (not visible)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		} else if(element instanceof UserDBResourceDAO) {
			UserDBResourceDAO dao = (UserDBResourceDAO)element;
			
			return dao.getFilename();
		}
		
		return "## not set ##"; //$NON-NLS-1$
	}
}