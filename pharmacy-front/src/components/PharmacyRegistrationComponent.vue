<template>
    <div id="registration">
		    <h4 class="text-center text-white pt-5">Pharmacy Registration</h4>
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  

		                        <div class="form-group">
		                            <label for="pharmacyName" class="text-info">Pharmacy name:</label><br>
		                            <input type="text" name="pharmacyName" id="pharmacyName" class="form-control" required="" v-model="registration.pharmacyName">
		                        </div>

                                <div class="form-group">
		                            <label for="pharmacyName" class="text-info">Location:</label><br>
		                            <input type="text" name="pharmacyName" id="pharmacyName" class="form-control" required="" v-model="registration.pharmacyLocation">
		                        </div>

		                        <div class="form-group">
		                            <input type="submit" name="submit" class="btn btn-info btn-md" value="Registration" v-on:click="register(registration)">
		                        </div>

		                </div>
		            </div>
		        </div>
		    </div>
		</div>
</template>


<script>
export default {
    name: "PharmacyRegistrationComponent",

    data() {
        return {
			registration: {}

		};
    },

    mounted() {
    },

    methods: {

		register: function (registration) {
			console.log(registration)
        }
		
	},
};
</script>