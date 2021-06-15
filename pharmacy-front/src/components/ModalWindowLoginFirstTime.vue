<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">

            <div id="registration-row" class="row justify-content-center align-items-center">
                <div id="registration-column" class="col-md-6">
                    <div id="registration-box" class="col-md-12">  
                        <form onsubmit="return false;">     	
                            <h4 class="text-center " style="margin-bottom: 40px;">Password change</h4>

                            <div class="form-group">
                                <label for="username" >New password:</label><br>
                                <input type="password" name="newPassword" id="newPassword" class="form-control" v-model="newPassword" required=""
                                oninvalid="this.setCustomValidity('Enter new password.')"  oninput="setCustomValidity('')">
                            </div>

                            <div class="form-group">
                                <label for="password" >Confirm password:</label><br>
                                <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required="" v-model="confirmPassword">
                            </div>

                            <div class="form-group">
                                <button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="ChangePassword" @click="changePassword()">Confirm</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";

export default {
    name : "ModalWindowLoginFirstTime",

    components : {Button},

    props : {
        modal_show : Boolean,
        username: String
    },
    
    data() {
        return {
            newPassword: "",
            confirmPassword: "",
            reserveUserRole: "",
        }
    },

    methods : {
        changePassword: function()
        {
            if(this.isSomeFieldEmpty()) return;

            var changePasswordDTO = {                           
                username: this.username,
                password: this.newPassword
            };

            client({
                url: 'auth/firstTimeChangePassword',
                method: "POST",
				data: changePasswordDTO
            })
            .then((response) => 
            {
                this.$toasted.show("Password succesfully changed.", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                this.loginRedirect();
            });
            
        },

        checkPassword: function() {
			var inputPasswordRepeat = document.getElementById('confirmPassword');
			var inputPassword 		= document.getElementById('newPassword');
			if (inputPasswordRepeat.value != inputPassword.value) 
			{
            	inputPasswordRepeat.setCustomValidity('Password Must be Matching.');
			} 
			else 
			{
				inputPasswordRepeat.setCustomValidity('');
			}
		},

        isSomeFieldEmpty: function()
		{
			if(this.newPassword == '') return true;
			if(this.confirmPassword == '') return true;
			return false;
		},

		loginRedirect: function()
		{
            // Prvo radim logut
            this.$store.commit("changeLoggedUserRole", "GUEST")
            this.$store.commit("changeLoggedUsername", "")

            localStorage.removeItem("USER_TOKEN");
            localStorage.removeItem("USER_TYPE");
            localStorage.removeItem("USER_EXPIRES")
            localStorage.removeItem("USERNAME");

            this.$store.commit("changeLogginFirstTimeDisableNav", true);

            // Onda ga saljem da se loguje opet
			this.$router
                .push({ name: "LoginPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
		},

    },

    mounted() { 
        
    }


};
</script>


<style scoped>


#close_btn{
    height: 30px;
    width: 25px;
}

.v-text-field{
    margin: 10px 60px;
    width: 300px;
}

body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
  display: block; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 40%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
</style>