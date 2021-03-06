package com.hangum.db.browser.rap.core.actions.global;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.hangum.db.browser.rap.core.Activator;
import com.hangum.db.browser.rap.core.Messages;
import com.hangum.db.browser.rap.core.actions.connections.QueryEditorAction;
import com.hangum.db.commons.sql.define.DBDefine;
import com.hangum.db.dao.system.UserDBDAO;
import com.swtdesigner.ResourceManager;

/**
 * 현재 선택된 디비의 릴레이션 화면을 오픈합니다.
 * 
 * @author hangum
 *
 */
public class OpenQueryEditorAction extends Action implements ISelectionListener, IWorkbenchAction {
	private final IWorkbenchWindow window;
	private final static String ID = "com.hangum.db.browser.rap.core.actions.global.OpenQueryEditorAction"; //$NON-NLS-1$
	private IStructuredSelection iss;
	
	public OpenQueryEditorAction(IWorkbenchWindow window) {
		this.window = window;
		
		setId(ID);
		setText(Messages.OpenQueryEditorAction_1);
		setToolTipText(Messages.OpenQueryEditorAction_2);
		setImageDescriptor( ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "resources/icons/sql-query.png"));
		setEnabled(false);
		
		window.getSelectionService().addPostSelectionListener(this);
	}
	
	@Override
	public void run() {
		UserDBDAO userDB = (UserDBDAO)iss.getFirstElement();
		
		QueryEditorAction qea = new QueryEditorAction();
		qea.run(userDB);
	}
	

	@Override
	public void dispose() {
		window.getSelectionService().removePostSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IStructuredSelection sel = (IStructuredSelection)selection;
		if(sel != null) {
			if( sel.getFirstElement() instanceof UserDBDAO ) {
				UserDBDAO userDB = (UserDBDAO)sel.getFirstElement();
				if(DBDefine.getDBDefine(userDB.getType()) != DBDefine.MONGODB_DEFAULT) {				
					iss = sel;					
					setEnabled(true);
					
					return;
				} 
			} 
		} 
		
		setEnabled(false);
	}

}
