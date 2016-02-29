<?PHP
if(isset($_POST['txtProTitle']) &&  isset($_POST['txtProDesc']) 
	&& isset($_POST['txtProPrice']) && isset($_POST['txtProSaler'])){
	
	if($_POST['btnSubmit'] == "Update"){
		$pro_id = $_GET['id'];
		$pro_title = $_POST['txtProTitle'];
		$pro_desc = $_POST['txtProDesc'];
		$pro_price = $_POST['txtProPrice'];
		$pro_saler = $_POST['txtProSaler'];
		$pro_date = date('l jS \of F Y h:i:s A');
		
		include_once("connection.php");	
		$sql = "UPDATE tbl_product SET pro_title = '$pro_title', pro_desc = '$pro_desc', 
				pro_price = $pro_price, pro_saler = '$pro_saler', pro_date = '$pro_date' 
				WHERE pro_id = $pro_id";
		mysql_query($sql);
		header("location: ./");
	}
	else if($_POST['btnSubmit'] == "Delete"){
        include_once("connection.php");
        $pro_id = $_GET['id'];
        mysql_query("DELETE FROM tbl_product WHERE pro_id = $pro_id");
        echo "Data Deleted.";
        header("location: ./");
    }
}
else{
	$txtProId = "";
    $txtProTitle = "";
    $txtProDesc = "";
    $txtProPrice = "";
	$txtProSaler = "";
	$txtProDate = "";
    if(isset($_GET['id'])){
        $txtProTitle = $_GET['id'];
        include_once("connection.php");
        
		$result = mysql_query("SELECT pro_id, pro_title, pro_desc, pro_price, pro_saler, pro_date 
				FROM tbl_product WHERE pro_id = $txtProTitle");
        $row = mysql_fetch_assoc($result);
        $txtProId = $row['pro_id'];
		$txtProTitle = $row['pro_title'];
		$txtProDesc = $row['pro_desc'];
        $txtProPrice = $row['pro_price'];
        $txtProSaler = $row['pro_saler'];
		$txtProDate = $row['pro_date'];
    }
    else{
        echo "No Data to Edit. Please go back to main page.";
    }
}
?>
<html>
<head>
    <title>Edit New Data</title>
    <script>
        function deleteConfirm() {
            var result = confirm("Are you sure to delete?");
            return result;
        }
    </script>
</head>
<body>
<h1>Edit New Data</h1>
<p>
    <a href="index.php">Back to Main page</a>
</p>

<form action="<?PHP $_PHP_SELF ?>" method="post">
    Product ID<input type="text" name="txtProId" value="<?PHP echo $txtProId; ?>" ><br/>
	Product Name<input type="text" name="txtProTitle" value="<?PHP echo $txtProTitle; ?>" ><br/>
    Desc<input type="text" name="txtProDesc" value="<?PHP echo $txtProDesc; ?>" ><br/>
	Price<input type="text" name="txtProPrice" value="<?PHP echo $txtProPrice; ?>" <br/>
    Saler<input type="text" name="txtProSaler" value="<?PHP echo $txtProSaler; ?>" ><br/>
	Date<input type="text" name="txtProDate" value="<?PHP echo $txtProDate; ?>"><br/>
	
    <input type="submit" name="btnSubmit" value="Update">
    <input type="submit" name="btnSubmit" value="Delete" onclick="return deleteConfirm()">
</form>

</body>

</html>