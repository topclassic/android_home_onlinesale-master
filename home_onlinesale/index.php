<?PHP
if(isset($_GET['format']) && $_GET['format'] == "json")
{
	$sql="";
	if(isset($_GET['id'])){
		$id=$_GET['id'];
		$sql = "SELECT pro_id, pro_title, pro_desc, pro_price, pro_saler, pro_date 
				FROM tbl_product WHERE pro_id = $id";
	}
	else{
		$sql = "SELECT pro_id, pro_title, pro_desc, pro_price, pro_saler, pro_date FROM tbl_product";
	}
	
	include_once("connection.php");	
	$result = mysql_query($sql) or die('Error in query: $query' . mysql_error());
		
	while($row = mysql_fetch_assoc($result)){
		$output[] = $row;
	}
	
	print (json_encode($output));
}
else
{
	$sql="";
	if(isset($_GET['id'])){
		$id=$_GET['id'];
		$sql = "SELECT pro_id, pro_title, pro_desc, pro_price, pro_saler, pro_date 
				FROM tbl_product WHERE pro_id = $id";
	}
	else{
		$sql = "SELECT pro_id, pro_title, pro_desc, pro_price, pro_saler, pro_date FROM tbl_product";
	}
	
	include_once("connection.php");
	$result = mysql_query($sql);
?>
	<html>
	<head>
		<title>View Data</title>
		<link rel="stylesheet" type="text/css" href="media/css/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="media/resources/syntax/shCore.css">
		<link rel="stylesheet" type="text/css" href="media/resources/demo.css">
		<style type="text/css" class="init">

		</style>
		<script type="text/javascript" language="javascript" src="media/js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="media/js/jquery.dataTables.js"></script>
		<script type="text/javascript" language="javascript" src="media/resources/syntax/shCore.js"></script>
		<script type="text/javascript" language="javascript" src="media/resources/demo.js"></script>
		<script type="text/javascript" language="javascript" class="init">
			$(document).ready(function() {
				$('#table1').dataTable();
			} );
		</script>
		</head>
	<body>
	<h1>View Data</h1>
	<p>
		<a href="addnew.php">Add New Data</a>
	</p>

	<table id="table1" class="display">
		<thead>
		<tr>
			<td>Pro ID</td>
			<td>Pro Title</td>
			<td>Desc</td>
			<td>Price</td>
			<td>Saler</td>
			<td>Date</td>
			<td>Action</td>
		</tr>
		</thead>
		<tbody>
		<?PHP while(($row = mysql_fetch_assoc($result)) == true){ ?>
			<tr>
				<td><?PHP echo $row['pro_id']; ?></td>
				<td><?PHP echo $row['pro_title']; ?></td>
				<td><?PHP echo $row['pro_desc']; ?></td>
				<td><?PHP echo $row['pro_price']; ?></td>
				<td><?PHP echo $row['pro_saler']; ?></td>
				<td><?PHP echo $row['pro_date']; ?></td> 
				<td><a href="edit.php?id=<?PHP echo $row['pro_id']; ?>">Edit</a></td>
			</tr>
		<?PHP } ?>
		</tbody>
	</table>

	</body>
	</html>
<?PHP
}
?>