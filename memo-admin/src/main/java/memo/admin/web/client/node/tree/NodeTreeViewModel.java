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
package memo.admin.web.client.node.tree;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.TreeViewModel;

import memo.domain.web.client.model.NodeModel;

public class NodeTreeViewModel implements TreeViewModel {

	@Override
	public boolean isLeaf(Object value) {
		GWT.log("NodeTreeViewModel.isLeaf " + value);

		NodeModel node = (NodeModel) value;
		return Boolean.TRUE.equals(node.getLeaf());
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		GWT.log("NodeTreeViewModel.getNodeInfo " + value);

		NodeModel node = (NodeModel) value;
		NodeDataProvider dataProvider = new NodeDataProvider(node);
		return new DefaultNodeInfo<NodeModel>(dataProvider, new NodeCell());
	}

	private static class NodeCell extends AbstractCell<NodeModel> {

		@Override
		public void render(Context context, NodeModel node, SafeHtmlBuilder sb) {
			if (node != null && node.getCode() != null) {
				sb.appendEscaped(node.getCode());
			}
		}
	}
}
