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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import memo.domain.dao.model.AuditableRule;
import memo.domain.dao.model.Hierarchical;

@Entity
@Table(name = "memo_node")
public class Node extends AuditableRule implements Hierarchical {

	private static final long serialVersionUID = 1L;

	private String code;
	private String urlCode;
	private String actionCode;

	private Node parentNode;
	private Map<String, Node> childNodes;

	private boolean leaf;
	private boolean hidden;

	/**
	 * Unique code identifier, <br>
	 * relative to the parent context.
	 */
	@Column(nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Unique url identifier.
	 */
	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	/**
	 * System action identifier.
	 */
	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	/**
	 * Helper method.
	 */
	@Transient
	public boolean isRoot() {
		return getParentNode() == null;
	}

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

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
