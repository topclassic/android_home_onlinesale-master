<?PHP

if(isset($_POST['txtProTitle']) && 
	isset($_POST['txtProDesc']) && 
	isset($_POST['txtProPrice']) && 
	isset($_POST['txtProSaler'])	
	){
    include_once("connection.php");
    $title = $_POST['txtProTitle'];
    $desc = $_POST['txtProDesc'];
	$price = $_POST['txtProPrice'];
    $saler = $_POST['txtProSaler'];
	$today = date('l jS \of F Y h:i:s A');
    mysql_query("Insert Into tbl_product(pro_title, pro_desc, pro_price, pro_saler, pro_date) Values('$title', '$desc', '$price', '$saler', '$today')");
    echo "Data Added.";
}

?>
<html>
    <head>
        <title>Add New Data</title>
    </head>
<body>
    <h1>Add New Data</h1>
    <p>
        <a href="index.php">Back to Main page</a>
    </p>
    <form action="<?PHP $_PHP_SELF ?>" method="post">
        Title<input type="text" name="txtProTitle" value=""><br/>
        Desc<input type="text" name="txtProDesc" value="" ><br/>
        Price<input type="text" name="txtProPrice" value="" ><br/>
        Saler<input type="text" name="txtProSaler" value="" ><br/>
        Date:<?PHP $today = date('l jS \of F Y h:i:s A'); echo $today; ?><br/>
        <input type="submit" name="btnSubmit" value="Add">
    </form>
</body>
</html>