<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<html>
	<body bgcolor="grewf">
		<table height="100%" width="100%" border="2" bgcolor="#80ffff">
			<tbody>
				<tr>
					<td colspan="2">
						<tiles:insert attribute="header"></tiles:insert>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="30%">
						<tiles:insert attribute="menu"></tiles:insert>
					</td>
					<td colspan="1">
						<tiles:insert attribute="body"></tiles:insert>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<tiles:insert attribute="footer"></tiles:insert>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>