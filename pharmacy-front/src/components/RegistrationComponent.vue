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
		                            <input type="password" name="password" id="password" class="form-control" required="" v-model="registration.password"
									oninvalid="this.setCustomValidity('Enter password.')"  oninput="setCustomValidity('')">
		                        </div>
								<div class="form-group">
		                            <label for="passwordRepeat" >Repeat password:</label><br>
		                            <input type="password" name="passwordRepeat" id="passwordRepeat" class="form-control" required="" v-model="passwordRepeat">
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

								<div class="form-group">
		                            <label for="city" >City:</label><br>
		                            <input type="text" name="city" id="city" class="form-control" v-model="registration.location.city">
		                        </div>
								<div class="form-group">
		                            <label for="street" >Street:</label><br>
		                            <input type="text" name="street" id="street" class="form-control" v-model="registration.location.street">
		                        </div>
								<div class="form-group">
		                            <label for="streetNum" >Street number:</label><br>
		                            <input type="number" name="streetNum" id="streetNum" class="form-control" v-model="registration.location.streetNum">
		                        </div>

		                        <!-- <div class="form-group">
		                            <label for="date" class="text-info">Birth date:</label><br>
		                            <input type="date" name="date" id="date" class="form-control" required="" v-model="registration.birthDate"
									oninvalid="this.setCustomValidity('Select date of birth.')"  oninput="setCustomValidity('')">
		                        </div> -->
								<div class="form-group" v-if="isPharmacist()">
									<div class="md-form mx-5 my-5">
										<label class="label" for="start_time">Choose starting work time</label>
										<input type="time" id="start_time" class="form-control" required=""
										oninvalid="this.setCustomValidity('Enter starting time.')"  oninput="setCustomValidity('')">
									</div>
									<div class="md-form mx-5 my-5">
										<label class="label" for="end_time">Choose ending work time</label>
										<input type="time" id="end_time" class="form-control" required=""
										oninvalid="this.setCustomValidity('Enter ending time.')"  oninput="setCustomValidity('')">
									</div>
								</div>
								<div v-if="typeToRegister=='PHARMACY_ADMIN'">
									<label class="label">Choose pharmacy</label>
									<PharmaciesWithSelect :pharmacies="pharmacies" @pharmacySelected="chosenPharmacySelected"></PharmaciesWithSelect>
								</div>
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
import moment from 'moment';
import PharmaciesWithSelect from "./PharmaciesWithSelect.vue";

export default {
    name: "UserRegistration",

	components: { PharmaciesWithSelect },

	props: {
		pharmacyId : {
			type : Number,
			default() {
				return -1;
			}
        },
        typeToRegister: String
    },

    data() {
        return {
			registration: {
				location: {
					city: "",
					street: "",
					streetNum: 0
				}
			},
			passwordRepeat: "",
			url: "",
			title: "",
			pharmacies: []
		};
    },

    mounted() {
		document.getElementById("male").checked = true;
		this.registration.gender = "MALE";
		this.setUrl();
		this.setUserRole();
		this.setTitle();
		if(this.typeToRegister == 'PHARMACY_ADMIN')
		{
			this.registration.pharmacyId = -1;
			client({
					url: "pharmacy/all",
					method: "GET",
				}).then((response) => {
				this.pharmacies = response.data;
        	});
		}
    },

    methods: {

		register: function (registration) {
			this.checkPassword();
			if(!this.isMailValid()) return;
			//if(registration.birthDate == undefined) return;
			if(this.isSomeFieldEmpty(registration)) return;

			if(this.typeToRegister === "PHARMACIST")
				if(this.checkInputFields()) return true;

			const url =
			"https://nominatim.openstreetmap.org/search/" +
			registration.location.city +
			", " +
			registration.location.street +
			" " +
			registration.location.streetNum;
			
			this.axios.get(
                url, {
                params: {
                    format: "json",
                    limit: 1, 
                    "accept-language": "en",
                },
            })
			.then((response) => 
			{
				if (response.data && response.data.lenght != 0) 
				{
                    registration.location['longitude'] = response.data[0].lon;
                    registration.location['latitude'] = response.data[0].lat;
                    registration.location['zipcode'] = 21000;

					client({
						url: this.url,
						method: "POST",
						data: registration
					}).then((response) => {
						if(this.pharmacyId != -1)
							this.createPharmacistEmployment(response.data);
						else
						{
							this.homeRedirect(); 
							this.$toasted.show("Registration ended successfully. Check your email to confirm it.", {
								theme: "toasted-primary",
								position: "top-center",
								duration: 10000,
							});
							this.$emit('registered', response.data); // vracamo objekat koji je registrovan
						}
					}).catch((response) => {
						this.$toasted.show("Username is taken. Try another.", {
							theme: "toasted-primary",
							position: "top-center",
							duration: 5000,
						});
					});

				}
			})
			.catch(() => 
			{
                this.$toasted.show("Could not find coordinates based on given info.", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
            });

			
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
			if(registration.location.city == '') return true;
			if(registration.location.street == '') return true;
			if(registration.location.streetNum == '' || registration.location.streetNum < 1) return true;
			if(this.typeToRegister === "PHARMACIST")
			{
				if(document.getElementById('start_time').value == '') return true;
				if(document.getElementById('end_time').value == '')   return true;
			}
			if(this.typeToRegister == "PHARMACY_ADMIN")
				if(registration.pharmacyId == -1) return true;
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
			else if(this.typeToRegister == 'PHARMACIST') 	{ this.url = 'pharmacist/create'; }
		},

		setUserRole()
		{
			if(this.typeToRegister == 'PHARMACY_ADMIN') 	{ this.registration.userRole = 'PHARMACY_ADMIN'; }
			else if(this.typeToRegister == 'PATIENT')   	{ this.registration.userRole = 'PATIENT'; }
			else if(this.typeToRegister == 'SYSTEM_ADMIN')  { this.registration.userRole = 'SYSTEM_ADMIN'; }
			else if(this.typeToRegister == 'SUPPLIER')  	{ this.registration.userRole = 'SUPPLIER'; }
			else if(this.typeToRegister == 'DERMATOLOGIST') { this.registration.userRole = 'DERMATOLOGIST'; }
			else if(this.typeToRegister == 'PHARMACIST') 	{ this.registration.userRole = 'PHARMACIST'; }
		},

		setTitle()
		{
			if(this.typeToRegister == 'PHARMACY_ADMIN') 	{ this.title = 'Pharmacy admin registration'; }
			else if(this.typeToRegister == 'PATIENT')  	 	{ this.title = 'Registration'; }
			else if(this.typeToRegister == 'SYSTEM_ADMIN')  { this.title = 'System admin registration'; }
			else if(this.typeToRegister == 'SUPPLIER')  	{ this.title = 'Supplier registration'; }
			else if(this.typeToRegister == 'DERMATOLOGIST') { this.title = 'Dermatologist registration'; }
			else if(this.typeToRegister == 'PHARMACIST') 	{ this.title = 'Pharmacist registration'; }
		},
		isPharmacist: function(){
			return this.typeToRegister === "PHARMACIST"; // Bojan pravio 
		},
		checkInputFields : function(){
            if(document.getElementById('start_time') === undefined || document.getElementById('end_time') === undefined){
                return true;
            }
            else {
                if(document.getElementById('start_time').value == ''){
                    this.$toasted.show("Please insert starting time!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
                if(document.getElementById('end_time').value == ''){
                    this.$toasted.show("Please insert ending time!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
				let time1 = moment().format(document.getElementById('start_time').value, 'HH:mm');
				let time2 = moment().format(document.getElementById('end_time').value, 'HH:mm');

				if(time1 >= time2){
					this.$toasted.show("Starting time must be before ending time!", {
						theme: "toasted-primary",
						position: "top-center",
						duration: 2000,
					});
					return true;
				}
			}
            return false;
        },
		createPharmacistEmployment: function(pharmacist){ // Bojan pravio
			let time1 = moment().format(document.getElementById('start_time').value, 'HH:mm');
			let time2 = moment().format(document.getElementById('end_time').value, 'HH:mm');

			let myWorkTime = {
				startDate: moment().format("YYYY-MM-DD"),
				startTime: time1,
				endDate: moment().format("YYYY-MM-DD"),
				endTime: time2
			};
			client({
				url: "employments",
				method: "POST",
				data: {
					employee: {
						id: pharmacist.id
					},
					workTime: myWorkTime,
					pharmacy: {
						id: this.pharmacyId
					},
					contractType: "PHARMACIST_CONTRACT",
				}
			}).then((response) => {
				pharmacist['workTime'] = myWorkTime;
				pharmacist['employmentId'] = response.data.id;
				this.$emit('registered', pharmacist);
			}).catch((response) => console.log("Hiring failed!"));
		},

		chosenPharmacySelected: function(id)
		{
			this.registration.pharmacyId = id;
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
