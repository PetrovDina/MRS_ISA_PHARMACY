<template>
    <div id="registration">
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  
							<form onsubmit="return false;">     	
		                        <h4 class="text-center text-info" style="margin-bottom: 40px;">Registration</h4>
		                        <div class="form-group">
		                            <label for="username" class="text-info">Username:</label><br>
		                            <input type="text" name="username" id="username" class="form-control" v-model="registration.username" required=""
									oninvalid="this.setCustomValidity('Enter username.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="password" class="text-info">Password:</label><br>
		                            <input type="text" name="password" id="password" class="form-control" required="" v-model="registration.password"
									oninvalid="this.setCustomValidity('Enter password.')"  oninput="setCustomValidity('')">
		                        </div>
								<div class="form-group">
		                            <label for="passwordRepeat" class="text-info">Repeat password:</label><br>
		                            <input type="text" name="passwordRepeat" id="passwordRepeat" class="form-control" required="" v-model="registration.passwordRepeat">
		                        </div>
		                        <div class="form-group">
		                            <label for="name" class="text-info">First name:</label><br>
		                            <input type="text" name="name" id="name" class="form-control" required="" v-model="registration.firstName"
									oninvalid="this.setCustomValidity('Enter name.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="lastname" class="text-info">Last name:</label><br>
		                            <input type="text" name="lastname" id="lastname" class="form-control" required="" v-model="registration.lastName"
									oninvalid="this.setCustomValidity('Enter lastname.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="gender" class="text-info">Gender:</label><br>
		                            <input type="radio" id="male" name="gender" value="MALE" v-model="registration.gender">
									<label for="male" style="color:#17a2b8">Male</label>
									<input type="radio" id="female" name="gender" value="FEMALE" v-model="registration.gender" checked>
									<label for="female" style="color:#17a2b8">Female</label>
		                        </div>
		                        <div class="form-group">
		                            <label for="date" class="text-info">Birth date:</label><br>
		                            <input type="date" name="date" id="date" class="form-control" required="" v-model="registration.birthDate"
									oninvalid="this.setCustomValidity('Select date of birth.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
									<!-- <button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Registration" @click="register(registration)">Register</button> -->
		                        	<Button @action-performed="register(registration)" class="btn-success" text="Register" color="rgba(15, 95, 72, 0.95)"> </Button>
								</div>
							</form>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
</template>


<script>

import Button from '../components/Button.vue';

export default {
    name: "PharmacyAdminRegistration",
    components: {
		Button
	},
	
	data() {
        return {
			registration: {}
		};
    },
    mounted() {
		document.getElementById("male").checked = true;
    },
    methods: {
		register: function (registration) {
			this.checkPassword();
			if(registration.birthDate == undefined) return;
			console.log(registration);
        },

		checkPassword: function() {
			if (document.getElementById('passwordRepeat').value != document.getElementById('password').value) {
            	document.getElementById('passwordRepeat').setCustomValidity('Password Must be Matching.');
			} 
			else 
			{
				document.getElementById('passwordRepeat').setCustomValidity('');
			}
		},


			
	},
};
</script>

<style scoped>
/*Dina samo isprobavala, ovo !important se ne preporucuje da se koristi, bolje napraviti class="nekoIme" htmlu pa tu klasu gadjati u css-u i menjati boju */
label{
   color: rgba(15, 95, 72, 0.95)  !important; 
}
h4{
    color: rgba(15, 95, 72, 0.95)  !important; 
}
</style>