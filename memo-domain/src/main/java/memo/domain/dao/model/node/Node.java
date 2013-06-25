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
package memo.domain.dao.model.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import memo.domain.dao.model.core.Resource;

@Entity
@Table(name = "memo_node")
public class Node extends Resource {

	private static final long serialVersionUID = 1L;

	private Node parentNode;
	private Map<String, Node> childNodes;
	private List<NodeRule> nodeRules;

	/**
	 * The hierarchical parent.
	 */
	@Override
	@ManyToOne(fetch = FetchType.LAZY)
	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * Helper method.
	 */
	public boolean hasChildNodes() {
		return childNodes != null && !childNodes.isEmpty();
	}

	@MapKey(name = "code")
	@OneToMany(mappedBy = "parentNode",
	cascade = { CascadeType.REMOVE })
	public Map<String, Node> getChildNodes() {
		if (childNodes == null) {
			childNodes = new HashMap<String, Node>();
		}

		return childNodes;
	}

	public void setChildNodes(Map<String, Node> childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * Helper method.
	 */
	public Node getChildNode(String code) {
		return getChildNodes().get(code);
	}

	/**
	 * Helper method.
	 */
	public Node createChildNode(String code) {
		Node node = new Node();
		getChildNodes().put(code, node);
		node.setParentNode(this);
		node.setCode(code);
		return node;
	}

	@OrderBy("pos")
	@OneToMany(mappedBy = "node",
	cascade = { CascadeType.REMOVE })
	public List<NodeRule> getNodeRules() {
		if (nodeRules == null) {
			nodeRules = new ArrayList<NodeRule>();
		}

		return nodeRules;
	}

	public void setNodeRules(List<NodeRule> nodeRules) {
		this.nodeRules = nodeRules;
	}

	/**
	 * Helper method.
	 */
	public NodeRule createNodeRule() {
		NodeRule nodeRule = new NodeRule();
		nodeRule.setNode(this);
		return nodeRule;
	}
}
