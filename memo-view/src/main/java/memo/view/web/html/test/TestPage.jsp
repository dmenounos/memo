<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="http://mojo.example.org/component" %>

<div class="main container">
	<div class="${bean.id} row">
		<div class="col-md-12 col">

			<h2>TEST LOGIN PAGE</h2>

			<a href='${contextPath}/app/'>HOME</a>

			<ul>
				<li>Nickname: ${contextUser.nickname}</li>
				<li>Fullname: ${contextUser.fullname}</li>
				<li>Gender:   ${contextUser.gender}</li>
				<li>Postcode: ${contextUser.postcode}</li>
				<li>Email:    ${contextUser.email}</li>
				<li>Country:  ${contextUser.country}</li>
				<li>Language: ${contextUser.language}</li>
				<li>Timezone: ${contextUser.timezone}</li>
			</ul>

			<m:component key="PersonGrid" />
			<m:component key="PetGrid" />

		</div>
	</div>
</div>
