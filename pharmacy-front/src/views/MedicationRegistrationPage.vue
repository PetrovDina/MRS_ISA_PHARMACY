<template>
    <div id="medicationRegistration">
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  
							<form onsubmit="return false;">  

		                        <h4 class="text-center " style="margin-bottom: 40px;">Medication Registration</h4>
		                        <div class="form-group">
		                            <label for="name" >Name:</label><br>
		                            <input type="text"  name="name" id="name" class="form-control" v-model="registration.name" required=""
									oninvalid="this.setCustomValidity('Enter medication name.')"  oninput="setCustomValidity('')">
		                        </div>
								<div class="form-group">
		                            <label for="content" >Content:</label><br>
		                            <input type="text" name="content" id="content" class="form-control" required="" v-model="registration.content"
									oninvalid="this.setCustomValidity('Enter medication content.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <label for="manufacturer" >Manufacturer:</label><br>
		                            <input type="text" name="manufacturer" id="manufacturer" class="form-control" required="" v-model="registration.manufacturer"
									oninvalid="this.setCustomValidity('Enter medication manufacturer.')"  oninput="setCustomValidity('')">
		                        </div>
		                        <div class="form-group">
		                            <input type="radio" id="req" name="prescriptionReq" value=true v-model="registration.prescriptionReq">
									<label for="req" >Prescription required</label>
									<input type="radio" id="notReq" name="prescriptionReq" value=false v-model="registration.prescriptionReq">
									<label for="notReq" >Not required</label>
		                        </div>
								<div class="form-group">
		                            <label for="description" >Description:</label><br>
		                            <textarea rows="5" cols="80" name="description" id="description" class="form-control" required="" v-model="registration.description"
									oninvalid="this.setCustomValidity('Enter medication description.')"  oninput="setCustomValidity('')">
                                    </textarea>
		                        </div>
                                <div class="dropdown" style="margin-top: 50px;">
                                        <label for="dropdownFormButton" >Form:</label>
                                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownFormButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                                        style="background: rgba(15, 95, 72, 0.95)">
                                            {{medicationFormLabel}}
                                        </button>
                                        
                                        <div class="dropdown-menu" aria-labelledby="dropdownFormButton">
                                        
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Powder')">Powder</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Capsule')">Capsule</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Pill')">Pill</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Oinment')">Oinment</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Paste')">Paste</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Gel')">Gel</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Solution')">Solution</a>
                                            <a class="dropdown-item" v-on:click="updateMedicationForm('Syrup')">Syrup</a>

                                        </div>

                                </div>

                                <div class="form-group">
                                    <button id="regBtn" style="visibility:hidden; margin-bottom: -200px;" class="btn btn-info btn-md" 
                                    value="Registration" @click="register(registration)">Register Medication
                                    </button>
                                </div>

							</form>
		                </div>
		            </div>
		        </div>

                <div class="container" style="display: flex; flex-direction: row;">

                    <div class="col-md-6">
                        <AlternativeMedicationsTableComponent :toAdd="'true'" :medications="allMedications" @add-alternative="addToAlternative"></AlternativeMedicationsTableComponent> 
                    </div>

                    <div class="col-md-6">
                        <AlternativeMedicationsTableComponent :toAdd="'false'" :medications="alternatives" @add-alternative="removeAlternative"></AlternativeMedicationsTableComponent>
                    </div>

                </div>

                <div class="form-group">
					<button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Registration"
                     @click="activateFormButton">Register Medication</button>
				</div>



		    </div>
		</div>
</template>

<script>

import { client } from "@/client/axiosClient";
import AlternativeMedicationsTableComponent from "../components/AlternativeMedicationsTableComponent";

export default {

    name: "MedicationRegistrationPage",

    components:{
        AlternativeMedicationsTableComponent
    },

    data() {
        return {
			registration: {
                name: "",
                content: "",
                manufacturer: "",
                description: "",
                form: "PILL",
            },
            medicationFormLabel: "Pill",

            allMedications: {},

            alternatives: []
                
		};
    },

    mounted() {
		document.getElementById("req").checked = true;
		this.registration.prescriptionReq = true;

        client({
                url: "med/all",
                method: "GET",
            }).then((response) => (this.allMedications = response.data));
    },

    methods: {

        updateMedicationForm : function(form) 
        {
            this.medicationFormLabel = form;
            this.registration.form = form.toUpperCase();
        },

        addToAlternative : function(id)
        {
            for (let i = 0; i < this.allMedications.length; i++) 
            {
                if(this.allMedications[i].id == id)
                {
                    this.alternatives.push(this.allMedications[i]);
                    this.allMedications.splice(i, 1);
                }
            }
        },

        removeAlternative : function(id)
        {
            for (let i = 0; i < this.alternatives.length; i++) 
            {
                if(this.alternatives[i].id == id)
                {
                    this.allMedications.push(this.alternatives[i]);
                    this.alternatives.splice(i, 1);
                }
            }
        },

        activateFormButton : function()
        {
            document.getElementById('regBtn').click();
        },

        register : function(registration)
        {
            if(this.isSomeFieldEmpty(registration)) return;
            registration.alternatives = this.alternatives;
            // console.log(registration.alternatives[0]);
            console.log(registration);

            client({
                url: "med/create",
                method: "POST",
				data: registration
            }).then((response) => {alert("Registracija leka uspesno izvrsena."); this.homeRedirect(); }).
			catch((response) => (console.log(response)));
        },

        isSomeFieldEmpty: function(registration)
		{
			if(registration.name == '') return true;
			if(registration.content == '') return true;
			if(this.manufacturer == '') return true;
			if(registration.description == '') return true;
			return false;
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
		}


	},
};
</script>

<style scoped>
input:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

</style>