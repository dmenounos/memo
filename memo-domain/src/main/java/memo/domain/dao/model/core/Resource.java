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
package memo.domain.dao.model.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import memo.domain.dao.model.AuditableEntity;

@Entity
@Table(name = "memo_resource")
public class Resource extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	private String code;
	private String hint;

	private Resource parentNode;
	private List<Resource> childNodes;
	private List<Permission> permissions;

	private boolean leaf;
	private boolean hidden;

	/**
	 * Unique identifier code, <br>
	 * relative to the parent context.
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Value type hint.
	 */
	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
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
	@ManyToOne(fetch = FetchType.LAZY)
	public Resource getParentNode() {
		return parentNode;
	}

	public void setParentNode(Resource parentNode) {
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
	public List<Resource> getChildNodes() {
		if (childNodes == null) {
			childNodes = new ArrayList<Resource>();
		}

		return childNodes;
	}

	public void setChildNodes(List<Resource> childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * Helper method.
	 */
	public Resource createChildNode(String code) {
		Resource node = new Resource();
		node.setParentNode(this);
		node.setCode(code);
		return node;
	}

	@OrderBy("pos")
	@OneToMany(mappedBy = "resource",
	cascade = { CascadeType.REMOVE })
	public List<Permission> getPermissions() {
		if (permissions == null) {
			return new ArrayList<Permission>();
		}

		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Helper method.
	 */
	public boolean hasPermissions() {
		return permissions != null && !permissions.isEmpty();
	}

	/**
	 * Helper method.
	 */
	public Permission createPermission() {
		Permission permission = new Permission();
		permission.setResource(this);
		return permission;
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

	@Override
	protected void buildString(StringBuilder sb) {
		super.buildString(sb);
		sb.append(", code: ").append(code);
		sb.append(", root: ").append(isRoot());
		sb.append(", leaf: ").append(isLeaf());
	}
}
