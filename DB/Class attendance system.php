<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$databasename = "android_project";
	// Create connection
	$conn = mysqli_connect($servername, $username, $password, $databasename);

if($_SERVER["REQUEST_METHOD"] == "POST")
	{
		$code = filter_input(INPUT_POST, "code");
		$domain = filter_input(INPUT_POST, "domain");
		$key = filter_input(INPUT_POST, "key");
		

			if ($code == "login" and $domain == "BUBT" and $key == "Attend_BUBT") {
			$username = filter_input(INPUT_POST, "username");
			$password = filter_input(INPUT_POST, "password");

			$sql = "select *, '054' as S_no,'spring-2021' as s_name, 
			'image.jpg' as imgdata from teacher where username = '".$username."' and password = '".$password."' limit 1";
			//$sql = "select * from teacher where username = 'shuvo1' and password = '123456'";
			$result=mysqli_query($conn,$sql);
			//$data = [];
			$data=array();
			while($row=mysqli_fetch_assoc($result)){
			/*
				$data = [
					'user' = $row;
				]
			*/
			$data["data"][]=$row;

			}
			header('Content-Type:Application/json');
			echo json_encode($data);
			}
			else if ($code == 'add') {
				$std_id=$_POST['std_id'];
				$course_code=$_POST['course_code'];
				$intake=$_POST['intake'];
				$section=$_POST['section'];
				$attn_day=$_POST['attn_day'];
				$Std_reg=$_POST['Std_reg'];

				//$sqll = "INSERT INTO tbl_registration (std_id, std_course_no, std_intk, std_sec,std_atndnc,RegisterORnot) VALUES ('".$std_id."','".$course_code."','".$intake."','".$section."','".$attn_day."','".$Std_reg."')";

				$sqll = "INSERT INTO tbl_registration (std_id, std_course_no, std_intk, std_sec,std_atndnc,RegisterORnot) VALUES ((SELECT DISTINCT ID from student_details where ID = '".$std_id."'),'".$course_code."','".$intake."','".$section."','".$attn_day."','".$Std_reg."')";

					// $sqli = "UPDATE tbl_registration SET std_atndnc ='".$attn_day."' WHERE std_id = '".$std_id."' and std_sec = '".$section."' and std_course_no = '".$course_code."' and std_intk = '".$intake."'";
		
				if(mysqli_query($conn,$sqll)){

					echo("Yes");

				}else{

					echo("No");

				}
			}
			else if ($code == "pro") {
				$td_code = filter_input(INPUT_POST, "teacher_code");

				$sql = "SELECT DISTINCT DEPT FROM teacher_course where Teacher_Code = '".$td_code."'";
				//$sql = "select * from teacher where username = 'shuvo1' and password = '123456'";
				$result=mysqli_query($conn,$sql);
				$data=array();
				while($row=mysqli_fetch_assoc($result)){
				$data["data"][]=$row;

				}
				//header('Content-Type:Application/json');
				echo json_encode($data);	
			}
			else if ($code == "course"){
				$td_code = filter_input(INPUT_POST, "teacher_code");
				$program = filter_input(INPUT_POST, "program");

				$sql = "SELECT DISTINCT Course_Code FROM teacher_course where Teacher_Code = '".$td_code."' and DEPT = '".$program."'";
				//$sql = "SELECT DISTINCT Course_Code FROM teacher_course where DEPT = '".$program."'";
				$result=mysqli_query($conn,$sql);
				$data=array();
				while($row=mysqli_fetch_assoc($result)){
					$data["data"][]=$row;

				}
				//header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "intake") {
				$td_code = filter_input(INPUT_POST, "teacher_code");
				$program = filter_input(INPUT_POST, "program");
				$course_code = filter_input(INPUT_POST, "course_code");

				$sql = "SELECT DISTINCT intake FROM teacher_course where Teacher_Code = '".$td_code."' and DEPT = '".$program."' and Course_Code = '".$course_code."'";
				//$sql = "SELECT DISTINCT intake FROM teacher_course where Course_Code = '".$course_code."'";
				$result=mysqli_query($conn,$sql);
				$data=array();
				while($row=mysqli_fetch_assoc($result)){
				$data["data"][]=$row;

				}
				//header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "sec") {
				$td_code = filter_input(INPUT_POST, "teacher_code");
				$program = filter_input(INPUT_POST, "program");
				$course_code = filter_input(INPUT_POST, "course_code");
				$intake = filter_input(INPUT_POST, "intake");

				$sql = "SELECT DISTINCT section FROM teacher_course where Teacher_Code = '".$td_code."' and DEPT = '".$program."' and Course_Code = '".$course_code."' and intake = '".$intake."'";
				//$sql = "SELECT DISTINCT section FROM teacher_course where intake = '".$intake."'";
				$result=mysqli_query($conn,$sql);
				$data=array();
				while($row=mysqli_fetch_assoc($result)){
					$data["data"][]=$row;

				}
				//header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "adhoc_id") {
				$section = filter_input(INPUT_POST, "section");
				$course_code = filter_input(INPUT_POST, "course_code");
				$intake = filter_input(INPUT_POST, "intake");

				$sql = "SELECT DISTINCT std_id FROM tbl_registration where std_sec = '".$section."' and std_course_no = '".$course_code."' and std_intk = '".$intake."'";
				//$sql = "SELECT std_id FROM tbl_registration";
				$result=mysqli_query($conn,$sql);
				$data=array();
				while($row=mysqli_fetch_assoc($result)){
				$data["data"][]=$row;

				}
				header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "student_data") {
				$section = filter_input(INPUT_POST, "section");
				$course_code = filter_input(INPUT_POST, "course_code");
				$intake = filter_input(INPUT_POST, "intake");

				//if ($data_view == "LIST_VIEW") {

				$sql = "SELECT DISTINCT std_id,(case WHEN RegisterORnot = 'U' then concat(Name,' (','Unreg',')') WHEN LENGTH(Role)>0 then concat(Name,' (',Role,')') else Name END) as std_name,std_atndnc FROM tbl_registration LEFT JOIN student_details ON( tbl_registration.std_id = student_details.ID) where std_sec = '".$section."' and std_course_no = '".$course_code."' and std_intk = '".$intake."'";

					//$sql = "SELECT DISTINCT std_id,std_name,std_atndnc,Role FROM tbl_registration where std_sec = '".$section."' and std_course_no = '".$course_code."' and std_intk = '".$intake."'";
				//}
				//else if ($data_view == "AD_HOC") {

				//	$sql = "SELECT DISTINCT std_id FROM tbl_registration where std_sec = '".$section."' and std_course_no = '".$course_code."' and std_intk = '".$intake."'";
				//}

				//$sql = "SELECT std_id FROM tbl_registration";
				$result=mysqli_query($conn,$sql);
				$data=array();
				while($row=mysqli_fetch_assoc($result)){
				$data["data"][]=$row;

				}
				header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "insert_CR") {
				$std_id = filter_input(INPUT_POST, "std_id");
				$s_no = filter_input(INPUT_POST, "S_no");
				$course_code = filter_input(INPUT_POST, "course_code");
				$sec = filter_input(INPUT_POST, "sec");
				$intake = filter_input(INPUT_POST, "intake");

				$sqli = "UPDATE tbl_registration set Role = 'CR' where std_id = '".$std_id."' and S_no = '".$s_no."' and std_course_no = '".$course_code."' and std_sec = '".$sec."' and std_intk = '".$intake."'";
				
				$result=mysqli_query($conn,$sqli);

				if ($result == true) {
					echo "ok";
				}
				else{
					echo "Error";
				}
			}
			else if ($code == "Remove_CR") {
				$std_id = filter_input(INPUT_POST, "std_id");
				$s_no = filter_input(INPUT_POST, "S_no");
				$course_code = filter_input(INPUT_POST, "course_code");
				$sec = filter_input(INPUT_POST, "sec");
				$intake = filter_input(INPUT_POST, "intake");

				$sqli = "UPDATE tbl_registration set Role = '' where std_id = '".$std_id."' and S_no = '".$s_no."' and std_course_no = '".$course_code."' and std_sec = '".$sec."' and std_intk = '".$intake."'";
				
				$result=mysqli_query($conn,$sqli);

				if ($result == true) {
					echo "ok";
				}
				else{
					echo "Error";
				}
			}
			else if ($code == "update_all_attend") {

				$T_Code = filter_input(INPUT_POST, "td_code");
				$dpt = filter_input(INPUT_POST, "Dept");
				$course_code = filter_input(INPUT_POST, "course_code");
				$int = filter_input(INPUT_POST, "intake");
				$sec = filter_input(INPUT_POST, "section");
				$Num_of_class = filter_input(INPUT_POST, "number_of_class");


				echo $Num_of_class."<br>";
				echo $T_Code."<br>";
				echo $course_code."<br>";
				echo $int."<br>";
				echo $sec."<br>";
				echo $dpt."<br>";

				$sqli = "UPDATE teacher_course SET Number_of_class ='".$Num_of_class."' WHERE Teacher_Code = '".$T_Code."' and DEPT = '".$dpt."' and Course_Code = '".$course_code."' and intake = '".$int."' and section = '".$sec."'";
				
				$result=mysqli_query($conn,$sqli);

				if ($result == true) {
					echo "ok";
				}
				else{
					echo "Error";
				}
			}
			else if ($code == "total_class") {

				$Teacher_Code = filter_input(INPUT_POST, "Teacher_Code");
				$DEPT = filter_input(INPUT_POST, "Dept");
				$course_code = filter_input(INPUT_POST, "course_code");
				$intake = filter_input(INPUT_POST, "intake");
				$section = filter_input(INPUT_POST, "section");
				$s_no = filter_input(INPUT_POST, "S_no");

				$sql = "SELECT DISTINCT Number_of_class FROM teacher_course where Teacher_Code = '".$Teacher_Code."' and DEPT = '".$DEPT."' and Course_Code = '".$course_code."' and intake = '".$intake."' and section = '".$section."' and S_no = '".$s_no."'";

				$result=mysqli_query($conn,$sql);
				$data=array();

				while($row=mysqli_fetch_assoc($result)){
					$data["data"][]=$row;

				}
				header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "std_attend_day") {

				$std_id=$_POST['std_id'];
				$section = filter_input(INPUT_POST, "section");
				$course_code = filter_input(INPUT_POST, "course_code");
				$intake = filter_input(INPUT_POST, "intake");

				$sql = "SELECT DISTINCT std_atndnc FROM tbl_registration where std_id = '".$std_id."' and std_sec = '".$section."' and std_course_no = '".$course_code."' and std_intk = '".$intake."'";

				$result=mysqli_query($conn,$sql);
				$data=array();
				
				while($row=mysqli_fetch_assoc($result)){
					$data["data"][]=$row;

				}
				header('Content-Type:Application/json');
				echo json_encode($data);
			}
			else if ($code == "student_ADD") {

				$Std_reg= filter_input(INPUT_POST, "Std_reg");
				$std_id = filter_input(INPUT_POST, "std_id");
				$s_no = filter_input(INPUT_POST, "S_no");
				$course_code = filter_input(INPUT_POST, "course_code");
				$section = filter_input(INPUT_POST, "section");
				$intake = filter_input(INPUT_POST, "intake");
				$attn_day = filter_input(INPUT_POST, "attn_day");

				
				//$sqll = "INSERT INTO tbl_registration (std_id, std_course_no, std_intk, std_sec,std_atndnc,RegisterORnot,S_no) VALUES ('".$std_id."','".$course_code."','".$intake."','".$section."','".$attn_day."','".$Std_reg."','".$s_no."')";

				$sqll = "INSERT INTO tbl_registration (std_id, std_course_no, std_intk, std_sec,std_atndnc,RegisterORnot,S_no) VALUES ((SELECT DISTINCT ID from student_details where ID = '".$std_id."'),'".$course_code."','".$intake."','".$section."','".$attn_day."','".$Std_reg."', '".$s_no."')";

				if(mysqli_query($conn,$sqll)){

					echo("Yes");

				}else{

					echo("No");

				}
			}
			else if ($code == "Total_attendance_update") {

				$T_Code = filter_input(INPUT_POST, "T_Code");
				$dpt = filter_input(INPUT_POST, "dpt");
				$course_code = filter_input(INPUT_POST, "course_code");
				$int = filter_input(INPUT_POST, "int");
				$sec = filter_input(INPUT_POST, "sec");
				$Num_of_class = filter_input(INPUT_POST, "Num_of_class");


				$sqli = "UPDATE teacher_course SET Number_of_class ='".$Num_of_class."' WHERE Teacher_Code = '".$T_Code."' and DEPT = '".$dpt."' and Course_Code = '".$course_code."' and intake = '".$int."' and section = '".$sec."'";
				
				$result=mysqli_query($conn,$sqli);

				if ($result == true) {
					echo "ok";
				}
				else{
					echo "Error";
				}
			}

			else if ($code == "std_attend_update") {

				$std_id = filter_input(INPUT_POST, "std_id");
				$s_no = filter_input(INPUT_POST, "S_no");
				$course_code = filter_input(INPUT_POST, "course_code");
				$section = filter_input(INPUT_POST, "section");
				$intake = filter_input(INPUT_POST, "intake");
				$attn_day = filter_input(INPUT_POST, "attn_day");

				$sql1 = "UPDATE tbl_registration SET std_atndnc = (case when (select count(std_atndnc) from tbl_registration WHERE std_id = '$std_id' and std_sec = '$section' and std_course_no = '$course_code' and std_intk = '$intake' and S_no = '$s_no' and std_atndnc like concat('%',left('".$attn_day."',2),'%'))>0 
					then replace(std_atndnc,REPEAT(left('".$attn_day."',2),(LENGTH(std_atndnc) - LENGTH(replace(std_atndnc, left('".$attn_day."',2), '')))/2),'".$attn_day."') 
					else concat(std_atndnc,'".$attn_day."') end) WHERE std_id = '$std_id' and std_sec = '$section' and std_course_no = '$course_code' and std_intk = '$intake' and S_no = '$s_no'";
				
				if(mysqli_query($conn,$sql1)){

					echo("Yes");

				}else{

					echo("No");

				}
			}
		
	}
?>