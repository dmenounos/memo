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
package memo.admin.web.server.node;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mojo.dao.core.DataPage;
import mojo.dao.core.DataService;
import mojo.dao.core.spec.ByKey;
import mojo.dao.core.spec.Select;

import memo.domain.dao.model.core.Resource;

@Controller
@RequestMapping("/node")
public class ResourceController {

	@Autowired
	@Qualifier("nodeService")
	private DataService<Resource> nodeService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<Resource> doSelect(@RequestParam(required = false) Integer parentNodeId) {
		ByKey filter = new ByKey().property("parentNode").key(parentNodeId);
		Select<Resource> select = new Select<Resource>(filter).order("code");
		DataPage<Resource> page = nodeService.select(select);
		return page.getData();
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void doInsert(@RequestBody Resource node) {
		nodeService.insert(node);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void doUpdate(@RequestBody Resource node) {
		nodeService.update(node);
	}
}
