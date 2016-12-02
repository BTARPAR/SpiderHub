<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
<script>
	function demoFromHTML() {
		var pdf = new jsPDF('p', 'pt', 'letter');
		// source can be HTML-formatted string, or a reference
		// to an actual DOM element from which the text will be scraped.
		source = $('#report')[0];
		// we support special element handlers. Register them with jQuery-style 
		// ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
		// There is no support for any other type of selectors 
		// (class, of compound) at this time.
		specialElementHandlers = {
			// element with id of "bypass" - jQuery style selector
			'#bypassme' : function(element, renderer) {
				// true = "handled elsewhere, bypass text extraction"
				return true
			}
		};
		margins = {
			top : 80,
			bottom : 60,
			left : 40,
			width : 522
		};
		// all coords and widths are in jsPDF instance's declared units
		// 'inches' in this case
		pdf.fromHTML(source, // HTML string or DOM elem ref.
		margins.left, // x coord
		margins.top, { // y coord
			'width' : margins.width, // max width of content on PDF
			'elementHandlers' : specialElementHandlers
		},

		function(dispose) {
			// dispose: object with X, Y of the last line add to the PDF 
			//          this allow the insertion of new lines after html
			pdf.save('Test.pdf');
		}, margins);
	}
</script>
<div class="jumbotron">
<div id="report">
	<h2>Project Name :</h2>
	<a>${project.projectName}</a>
	<h3>Project Description :</h3>
	<p>${project.projectDescription}</p>
	<table id="tab_customers" class="table table-hover">
		<colgroup>
			<col width="20%">
			<col width="30%">
			<col width="30%">
			<col width="30%">
		</colgroup>
		<thead>
			<tr>
				<th>Task Name</th>
				<th>Task Related User</th>
				<th>Task Status</th>
				<th>hrs spent</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tasksWeekly}" var="task" varStatus="status">
				<tr>
					<td>${task.taskName}</td>
					<td>${task.userTasks.userName}</td>
					<td>${task.statusTasks.statusName}</td>
					<td>${totalHourArrayWeekly[status.index]}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<button onclick="javascript:demoFromHTML();">PDF</button>
<br />
</div>