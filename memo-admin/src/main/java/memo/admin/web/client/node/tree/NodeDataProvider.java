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

import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import mojo.gwt.data.client.event.LoadEvent;
import mojo.gwt.data.client.event.LoadHandler;
import mojo.gwt.data.client.util.Observer;

import memo.domain.web.client.model.NodeModel;
import memo.domain.web.client.store.NodeStore;

public class NodeDataProvider extends AsyncDataProvider<NodeModel> {

	private NodeStore store;
	private NodeModel node;

	public NodeDataProvider(NodeModel node) {
		store = new NodeStore();
		this.node = node;
	}

	@Override
	public Object getKey(NodeModel item) {
		if (item != null) {
			return item.getId();
		}

		return null;
	}

	@Override
	protected void onRangeChanged(HasData<NodeModel> display) {
		store.addLoadHandler(new OnLoad(display));
		Range range = display.getVisibleRange();

		StringBuilder sb = new StringBuilder();
		sb.append("NodeDataProvider.onRangeChanged");
		sb.append(" nodeKey: " + getKey(node));
		sb.append(" range.start: " + range.getStart());
		sb.append(" range.length: " + range.getLength());
		GWT.log(sb.toString());

		store.getParams().put("parentNodeId", getKey(node));
		store.loadData();
	}

	private class OnLoad extends Observer implements LoadHandler {

		private HasData<NodeModel> display;

		public OnLoad(HasData<NodeModel> display) {
			this.display = display;
		}

		@Override
		public void onLoad(LoadEvent<?> e) {
			LoadEvent<NodeModel> event = e.cast();

			Range range = display.getVisibleRange();
			display.setRowCount(event.getData().size(), true);
			display.setRowData(range.getStart(), event.getData());

			getRegistration().removeHandler();
		}
	}
}
