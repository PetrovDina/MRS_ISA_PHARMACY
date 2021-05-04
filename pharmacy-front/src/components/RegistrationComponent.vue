<template>
    <div id="registration">
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  
							<form onsubmit="return false;">     	
		                        <h4 class="text-center " style="margin-bottom: 40px;">{{title}}</h4>
		                        <div class="form-group">
		                            <label for="username" >Username:</label><br>
		                            <input type="text" name="username" id="username" class="form-control" v-model="registration.username" required=""
									oninvalid="this.setCustomValidity('Enter username.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="password" >Password:</label><br>
		                            <input type="text" name="password" id="password" class="form-control" required="" v-model="registration.password"
									oninvalid="this.setCustomValidity('Enter password.')"  oninput="setCustomValidity('')">
		                        </div>
								<div class="form-group">
		                            <label for="passwordRepeat" >Repeat password:</label><br>
		                            <input type="text" name="passwordRepeat" id="passwordRepeat" class="form-control" required="" v-model="passwordRepeat">
		                        </div>
								<div class="form-group">
		                            <label for="email" >Email:</label><br>
		                            <input type="text" name="email" id="email" class="form-control" required="" v-model="registration.email"
									pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
		                        </div>
		                        <div class="form-group">
		                            <label for="name" >First name:</label><br>
		                            <input type="text" name="name" id="name" class="form-control" required="" v-model="registration.firstName"
									oninvalid="this.setCustomValidity('Enter name.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="lastname" >Last name:</label><br>
		                            <input type="text" name="lastname" id="lastname" class="form-control" required="" v-model="registration.lastName"
									oninvalid="this.setCustomValidity('Enter lastname.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="gender" >Gender:</label><br>
		                            <input type="radio" id="male" name="gender" value="MALE" v-model="registration.gender">
									<label for="male" >Male</label>
									<input type="radio" id="female" name="gender" value="FEMALE" v-model="registration.gender" checked>
									<label for="female" >Female</label>
		                        </div>
		                        <!-- <div class="form-group">
		                            <label for="date" class="text-info">Birth date:</label><br>
		                            <input type="date" name="date" id="date" class="form-control" required="" v-model="registration.birthDate"
									oninvalid="this.setCustomValidity('Select date of birth.')"  oninput="setCustomValidity('')">
		                        </div> -->
		                        <div class="form-group">
									<button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Registration" @click="register(registration)">Register</button>
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

export default {
    name: "UserRegistration",

	props: {
        typeToRegister: String,
    },

    data() {
        return {
			registration: {},
			passwordRepeat: "",
			url: "",
			title: ""
		};
    },

    mounted() {
		document.getElementById("male").checked = true;
		this.registration.gender = "MALE";
		this.setUrl();
		this.setUserRole();
		this.setTitle();
    },

    methods: {

		register: function (registration) {
			this.checkPassword();
			if(!this.isMailValid()) return;
			//if(registration.birthDate == undefined) return;
			if(this.isSomeFieldEmpty(registration)) return;
			
			registration.location = 
			{
					id: 4,
					latitude: 30.00,
					longitude: 30.00,
					street: 'Ljube Nesica',
					city: 'Zajecar',
					zipCode: '19000',
					streetNum: 21
			};

			client({
                url: this.url,
                method: "POST",
				data: registration
            }).then((response) => {this.homeRedirect(); alert("Registracija uspesno izvrsena.");}).
			catch((response) => (console.log(response)));
        },

		checkPassword: function() {
			var inputPasswordRepeat = document.getElementById('passwordRepeat');
			var inputPassword 		= document.getElementById('password');
			if (inputPasswordRepeat.value != inputPassword.value) 
			{
            	inputPasswordRepeat.setCustomValidity('Password Must be Matching.');
			} 
			else 
			{
				inputPasswordRepeat.setCustomValidity('');
			}
		},

		isSomeFieldEmpty: function(registration)
		{
			if(registration.username == '') return true;
			if(registration.password == '') return true;
			if(this.passwordRepeat == '') return true;
			if(registration.firstName == '') return true;
			if(registration.lastName == '') return true;
			if(registration.email == '') return true;
			return false;
		},

		isMailValid: function()
		{
			var inputMail = document.getElementById('email');
			if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(inputMail.value))
			{
				inputMail.setCustomValidity('');
				return true;
			}
			else
			{
				inputMail.setCustomValidity('Invalid email format.');
				return false;
			}
			
		},

		homeRedirect()
		{
			this.$router
                .push({ name: "Home" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
		},

		setUrl()
		{
			if(this.typeToRegister == 'PHARMACY_ADMIN') 	{ this.url = 'pharmacyAdmin/create'; }
			else if(this.typeToRegister == 'PATIENT')   	{ this.url = 'patient/create'; }
			else if(this.typeToRegister == 'SYSTEM_ADMIN')  { this.url = 'systemAdmin/create'; }
			else if(this.typeToRegister == 'SUPPLIER')  	{ this.url = 'supplier/create'; }
			else if(this.typeToRegister == 'DERMATOLOGIST') { this.url = 'dermatologist/create'; }
		},

		setUserRole()
		{
			if(this.typeToRegister == 'PHARMACY_ADMIN') 	{ this.registration.userRole = 'PHARMACY_ADMIN'; }
			else if(this.typeToRegister == 'PATIENT')   	{ this.registration.userRole = 'PATIENT'; }
			else if(this.typeToRegister == 'SYSTEM_ADMIN')  { this.registration.userRole = 'SYSTEM_ADMIN'; }
			else if(this.typeToRegister == 'SUPPLIER')  	{ this.registration.userRole = 'SUPPLIER'; }
			else if(this.typeToRegister == 'DERMATOLOGIST') { this.registration.userRole = 'DERMATOLOGIST'; }
		},

		setTitle()
		{
			if(this.typeToRegister == 'PHARMACY_ADMIN') 	{ this.title = 'Pharmacy admin registration'; }
			else if(this.typeToRegister == 'PATIENT')  	 	{ this.title = 'Registration'; }
			else if(this.typeToRegister == 'SYSTEM_ADMIN')  { this.title = 'System admin registration'; }
			else if(this.typeToRegister == 'SUPPLIER')  	{ this.title = 'Supplier registration'; }
			else if(this.typeToRegister == 'DERMATOLOGIST') { this.title = 'Dermatologist registration'; }
		}
	
	},
};
</script>

<style scoped>
.form-control:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

</style>
