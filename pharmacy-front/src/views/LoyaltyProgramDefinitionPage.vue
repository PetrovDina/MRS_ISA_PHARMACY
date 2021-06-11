<template>
    <div id="loyalty">

        <h4 class="text-center text-white pt-5">Loyalty program definition</h4>
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  

							<form onsubmit="return false;">
								<h4 class="text-center " style="margin-bottom: 40px;">Loyalty program definition</h4>

		                        <div class="form-group">
		                            <label for="afterAppointment" >Points to get after appointment:</label><br>
		                            <input type="number" min="0" max="12" name="afterAppointment" id="afterAppointment" class="form-control" v-model="loyalty.afterAppointment"
									required="" oninvalid="this.setCustomValidity('Value must be 12 or lower.')"  oninput="setCustomValidity('')">
		                        </div>

                                <div class="form-group">
		                            <label for="regular" >Upper bound for regular category:</label><br>
		                            <input type="number" min="0" name="regular" id="regular" class="form-control" v-model="loyalty.maxPointsRegular"
									required="" oninvalid="this.setCustomValidity('Value must be 0 or higher.')"  oninput="setCustomValidity('')">
		                        </div>

								<div class="form-group">
		                            <label for="silver" >Upper bound for silver category:</label><br>
		                            <input type="number" min="0" name="silver" id="silver" class="form-control" v-model="loyalty.maxPointsSilver"
									required="" oninvalid="this.setCustomValidity('Value must be 0 or higher.')"  oninput="setCustomValidity('')">
		                        </div>
								<div class="form-group">
		                            <label for="silverDis" >Discount for silver category (%):</label><br>
		                            <input type="number" min="0" max="99" name="silverDis" id="silverDis" class="form-control" v-model="loyalty.silverDis"
									required="" oninvalid="this.setCustomValidity('Discount must be less or equal than 99')"  oninput="setCustomValidity('')">
		                        </div>


								<div class="form-group">
		                            <label for="gold" >Lower bound (-1) for gold category:</label><br>
		                            <input type="number" min="0" name="gold" id="gold" class="form-control" v-model="loyalty.maxPointsSilver" disabled>
		                        </div>
								<div class="form-group">
		                            <label for="goldDis" >Discount for gold category (%):</label><br>
		                            <input type="number" min="0" max="100" name="goldDis" id="goldDis" class="form-control" v-model="loyalty.goldDis"
									required="" oninvalid="this.setCustomValidity('Discount must be less or equal than 100')"  oninput="setCustomValidity('')">
		                        </div>

		                        <div class="form-group">
		                            <input type="submit" name="submit" class="btn btn-info btn-md" style="background: rgba(15, 95, 72, 0.95)" value="Define" v-on:click="changeLoyalty(loyalty)">
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

    name: "LoyaltyProgramDefinitionPage",

    methods: {
       
       changeLoyalty: function(loyalty)
       {
		   if(this.isSomeFieldEmpty(loyalty))  return;
		   if(!this.arePointsInInterval(loyalty)) return;
		   if(!this.arePointsRegular(loyalty)) return;	  
		   
		   client({
				url: "loyalty/updateLoyalty",
				method: "PUT",
				data: loyalty
			})
			.then((response) => 
			{
				this.$toasted.show("Successfully defined loyalty program", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 3000,
                });
				this.$router.push({ name: "Home" }).catch((err) => 
				{
                    if (err.name != "NavigationDuplicated") {
                        console.error(err);
                    }
                });
			})
			.catch((response) => 
			{
				this.$toasted.show("Error ocured. Please try again.", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 5000,
                });
				client({
					url: "loyalty/getLoyalty",
					method: "GET",
				})
				.then((response) => 
				{
					this.loyalty.afterAppointment = response.data.afterAppointment;
					this.loyalty.maxPointsRegular = response.data.maxPointsRegular;
					this.loyalty.maxPointsSilver = response.data.maxPointsSilver;
					this.loyalty.silverDis = response.data.silverDis;
					this.loyalty.goldDis = response.data.goldDis;
				})
			})

       },

	   isSomeFieldEmpty: function(loyalty)
		{
			if(loyalty.afterAppointment === '') return true;
			if(loyalty.maxPointsRegular === '') return true;
			if(loyalty.maxPointsSilver === '') return true;
			if(loyalty.silverDis === '') return true;
			if(loyalty.goldDis === '') return true;

			return false;
		},

		arePointsInInterval: function(loyalty)
		{
			if(loyalty.afterAppointment < 0 || loyalty.afterAppointment > 12)
			{
				return false;
			} 


			if(loyalty.maxPointsRegular < 0)
			{
				return false;
			} 

			if(loyalty.maxPointsSilver < 0)
			{

				return false;
			}

			if(loyalty.maxPointsGold < 0)
			{
				return false;
			}

			if(loyalty.silverDis < 0 || loyalty.silverDis > 99)
			{
				return false;
			}

			if(loyalty.goldDis < 0 || loyalty.goldDis > 100)
			{
				return false;
			}

			return true;
		},

		arePointsRegular: function(loyalty)
		{
			if(loyalty.maxPointsRegular >= loyalty.maxPointsSilver)
			{
				this.$toasted.show("Points for regular category must be lower than silver and gold", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
				return false;
			} 

			if(loyalty.silverDis >= loyalty.goldDis)
			{
				this.$toasted.show("Discount for silver must be lower than discount for gold", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
				return false;
			}

			return true;
		},


    },

    data() {
        return {
            loyalty: {

                afterAppointment: 0,
                maxPointsRegular: 0,
                maxPointsSilver: 0,
                maxPointsGold: 0,
				silverDis: 0,
				goldDis: 0
            },
        }
    },

	mounted() {

		client({
			url: "loyalty/getLoyalty",
			method: "GET",
        })
		.then((response) => 
		{
			this.loyalty.afterAppointment = response.data.afterAppointment;
            this.loyalty.maxPointsRegular = response.data.maxPointsRegular;
            this.loyalty.maxPointsSilver = response.data.maxPointsSilver;
            this.loyalty.silverDis = response.data.silverDis;
			this.loyalty.goldDis = response.data.goldDis;
		})

	}


}

</script>

<style scoped>
input:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

</style>