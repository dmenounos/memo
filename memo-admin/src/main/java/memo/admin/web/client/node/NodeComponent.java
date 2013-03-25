/*
 * Copyright (C) 2010 Dimitrios Menounos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package memo.admin.web.client.node;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import memo.admin.web.client.node.tree.NodeTreeViewModel;
import memo.domain.web.client.model.NodeModel;

public class NodeComponent extends Composite {

	interface Binder extends UiBinder<Widget, NodeComponent> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private NodeActivity activity;

	@UiField(provided = true)
	CellTree cellTree;

	public NodeComponent() {
		initCellTree();
		initWidget(binder.createAndBindUi(this));
	}

	protected void initCellTree() {
		NodeModel rootNode = new NodeModel();
		cellTree = new CellTree(new NodeTreeViewModel(), rootNode);
	}

	public NodeActivity getActivity() {
		return activity;
	}

	public void setActivity(NodeActivity activity) {
		this.activity = activity;
	}
}
