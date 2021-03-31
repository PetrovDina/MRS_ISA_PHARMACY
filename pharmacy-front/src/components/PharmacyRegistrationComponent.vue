<template>
    <div id="registration">
		    <h4 class="text-center text-white pt-5">Pharmacy Registration</h4>
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  

		                        <div class="form-group">
		                            <label for="pharmacyName" class="text-info">Pharmacy name:</label><br>
		                            <input type="text" name="pharmacyName" id="pharmacyName" class="form-control" required="" v-model="pharmacy.name">
		                        </div>

                                <div class="form-group">
		                            <label for="pharmacyName" class="text-info">Location:</label><br>
		                            <input type="text" name="pharmacyName" id="pharmacyName" class="form-control" required="" v-model="pharmacy.location">
		                        </div>

		                        <div class="form-group">
		                            <input type="submit" name="submit" class="btn btn-info btn-md" value="Registration" v-on:click="register(pharmacy)">
		                        </div>

		                </div>
		            </div>
		        </div>
		    </div>
		</div>
</template>


<script>
import { client } from "@/client/axiosClient";

export default {
    name: "PharmacyRegistrationComponent",

    data() {
        return {
			pharmacy: {}

		};
    },

    mounted() {
    },

    methods: {

		register: function (registration) {

			var p = 
            {
                id: null,
                name: this.pharmacy.name,
                location: {
					id: 4,
					latitude: 30.00,
					longitude: 30.00,
					street: 'Ljube Nesica',
					city: 'Zajecar',
					zipCode: '19000',
					streetNum: 21
				},
                rating: 0.0
            }

			client({
                url: "pharmacy/create",
                method: "POST",
				data: p
            }).then((response) => (this.homeRedirect())).
			catch((response) => (console.log(response)));
        },

		homeRedirect: function () {
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


		
	},
};
</script>