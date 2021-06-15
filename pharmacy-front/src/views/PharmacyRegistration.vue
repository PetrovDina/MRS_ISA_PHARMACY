<template>
    <div id="registration">
		    <h4 class="text-center text-white pt-5">Pharmacy Registration</h4>
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  
							<form onsubmit="return false;">
								<h4 class="text-center " style="margin-bottom: 40px;">Pharmacy Registration</h4>
		                        <div class="form-group">
		                            <label for="pharmacyName" >Pharmacy name:</label><br>
		                            <input type="text" name="pharmacyName" id="pharmacyName" class="form-control" v-model="pharmacy.name">
		                        </div>
                                <div class="form-group">
		                            <label for="city" >City:</label><br>
		                            <input type="text" name="city" id="city" class="form-control" v-model="pharmacy.location.city">
		                        </div>
								<div class="form-group">
		                            <label for="street" >Street:</label><br>
		                            <input type="text" name="street" id="street" class="form-control" v-model="pharmacy.location.street">
		                        </div>
								<div class="form-group">
		                            <label for="streetNum" >Street number:</label><br>
		                            <input type="number" name="streetNum" id="streetNum" class="form-control" v-model="pharmacy.location.streetNum">
		                        </div>
								<div class="form-group">
		                            <label for="examinationPrice" >Examination price:</label><br>
		                            <input type="text" name="examinationPrice" id="examinationPrice" class="form-control" v-model="pharmacy.appointmentPriceCatalog.examinationPrice">
		                        </div>

								<div class="form-group">
		                            <label for="consultationPrice" >Consultation price:</label><br>
		                            <input type="text" name="consultationPrice" id="consultationPrice" class="form-control" v-model="pharmacy.appointmentPriceCatalog.consultationPrice">
		                        </div>
		                        <div class="form-group">
		                            <input type="submit" name="submit" class="btn btn-info btn-md" style="background: rgba(15, 95, 72, 0.95)" value="Register" v-on:click="register(pharmacy)">
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
    name: "PharmacyRegistration",

    data() {
        return {
			pharmacy: {
				name : '',

				location : {
					city: "",
					street: "",
					streetNum: 0
				},

				appointmentPriceCatalog : {
					examinationPrice : '',
					consultationPrice : '',
				}
			},
		};
    },

    mounted() {
    },

    methods: {
		checkInputFields : function(pharmacy) 
		{
			if(pharmacy.name == '') 
			{
				this.$toasted.show("Please insert pharmacy name!", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
				return true;
			}

			if(pharmacy.location.city == '') 
			{
				this.$toasted.show("Please insert city!", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
				return true;
			}

			if(pharmacy.location.street == '') 
			{
				this.$toasted.show("Please insert street!", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
				return true;
			}

			if(pharmacy.location.street == '' || pharmacy.location.street < 1) 
			{
				this.$toasted.show("Please insert proper value for street number!", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
				return true;
			}

			if(pharmacy.appointmentPriceCatalog.examinationPrice == '' || parseInt(pharmacy.appointmentPriceCatalog.examinationPrice) <= 0) 
			{
				this.$toasted.show("Please insert a proper value for examination price!", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
				return true;
			}
			if( pharmacy.appointmentPriceCatalog.consultationPrice == '' || parseInt(pharmacy.appointmentPriceCatalog.consultationPrice) <= 0) 
			{
				this.$toasted.show("Please insert a proper value for consultation price!", {
					theme: "toasted-primary",
					position: "top-center",
					duration: 2000,
				});
				return true;
			}
			return false;
		},

		register: function (pharmacy) 
		{
			if(this.checkInputFields(pharmacy)) return;
			
			const url =
                "https://nominatim.openstreetmap.org/search/" +
                pharmacy.location.city +
                ", " +
                pharmacy.location.street +
                " " +
                pharmacy.location.streetNum;

			this.axios.get
			(
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
					pharmacy.location['longitude'] = response.data[0].lon;
                    pharmacy.location['latitude'] = response.data[0].lat;
                    pharmacy.location['zipcode'] = 21000;
					console.log(pharmacy);
                    client({
                        url: "pharmacy/create",
                        method: "POST",
                        data: pharmacy
                    })
					.then((response) => 
					{
                        this.$toasted.show("Pharmacy reigstration completed successfully.", {
							theme: "toasted-primary",
							position: "top-center",
							duration: 5000,
						});
						
						this.homeRedirect();
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
			})

			// client({
            //     url: "pharmacy/create",
            //     method: "POST",
			// 	data: pharmacy
            // }).then((response) => (this.homeRedirect())).
			// catch((response) => (console.log(response)));
        },

		homeRedirect: function () 
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


		
	},
};
</script>

<style scoped>
.form-control:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

</style>