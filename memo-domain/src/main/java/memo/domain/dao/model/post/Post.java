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
package memo.domain.dao.model.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import memo.domain.dao.model.AuditableRule;
import memo.domain.dao.model.Hierarchical;

@Entity
@Table(name = "memo_post")
public class Post extends AuditableRule implements Hierarchical {

	private static final long serialVersionUID = 1L;

	private String code;
	private String urlCode;
	private String actionCode;

	private Post parentNode;
	private List<Post> childNodes;

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
	public Post getParentNode() {
		return parentNode;
	}

	public void setParentNode(Post parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * Helper method.
	 */
	public boolean hasChildNodes() {
		return childNodes != null && !childNodes.isEmpty();
	}

	@OrderBy("id")
	@OneToMany(mappedBy = "parentNode",
	cascade = { CascadeType.REMOVE })
	public List<Post> getChildNodes() {
		if (childNodes == null) {
			childNodes = new ArrayList<Post>();
		}

		return childNodes;
	}

	public void setChildNodes(List<Post> childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * Helper method.
	 */
	public Post createChildNode(String code) {
		Post node = new Post();
		getChildNodes().add(node);
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
