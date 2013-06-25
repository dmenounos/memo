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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import memo.domain.dao.model.core.Resource;

@Entity
@Table(name = "memo_post")
public class Post extends Resource {

	private static final long serialVersionUID = 1L;

	private Post parentNode;
	private List<Post> childNodes;
	private List<PostRule> postRules;

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

	@OrderBy("pos")
	@OneToMany(mappedBy = "post",
	cascade = { CascadeType.REMOVE })
	public List<PostRule> getPostRules() {
		if (postRules == null) {
			postRules = new ArrayList<PostRule>();
		}

		return postRules;
	}

	public void setPostRules(List<PostRule> postRules) {
		this.postRules = postRules;
	}

	/**
	 * Helper method.
	 */
	public PostRule createPostRule() {
		PostRule nodeRule = new PostRule();
		nodeRule.setPost(this);
		return nodeRule;
	}
}
