<template>
    <div id="login">
		    <div class="container">
		        <div id="registration-row" class="row justify-content-center align-items-center">
		            <div id="registration-column" class="col-md-6">
		                <div id="registration-box" class="col-md-12">  
							<form onsubmit="return false;">     	
		                        <h4 class="text-center " style="margin-bottom: 40px;">Login</h4>

		                        <div class="form-group">
		                            <label for="username" >Username:</label><br>
		                            <input type="text" name="username" id="username" class="form-control" v-model="login.username" required=""
									oninvalid="this.setCustomValidity('Enter username.')"  oninput="setCustomValidity('')">
		                        </div>

		                        <div class="form-group">
		                            <label for="password" >Password:</label><br>
		                            <input type="password" name="password" id="password" class="form-control" required="" v-model="login.password"
									oninvalid="this.setCustomValidity('Enter password.')"  oninput="setCustomValidity('')">
		                        </div>

                                <div class="form-group">
									<button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Login" @click="loginMethod(login)">Login</button>
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

    name: "LoginPage",

    data() {
        return {
			login: {
                username: '',
                password: ''
            },

            url: 'auth/login/'
		};
    },

    methods: {

        loginMethod: function(login)
        {
            if(this.isSomeFieldEmpty(login)) return;

            //Parsiranje tokena - podesavanje uloge i cuvanje istog u localStorage

            client({
                url: this.url,
                method: "POST",
				data: login
            })
            .then((response) => 
            {
                console.log(response);
                var payload = this.parseJwt(response.data.accessToken);
                localStorage.user = payload.sub;
                localStorage.setItem("USER_TOKEN", response.data.accessToken);
                localStorage.setItem("USER_TYPE", payload.role);
                localStorage.setItem("USER_EXPIRES", response.data.expiresIn)
                localStorage.setItem("USERNAME", this.login.username); //dina dodala
                this.$store.commit("changeLoggedUsername", this.login.username) //dina dodala
                this.$store.commit("changeLoggedUserRole", payload.role);
                if(localStorage.getItem('USER_TYPE') == 'PHARMACY_ADMIN')
                    this.pharmacyAdminRedirect(localStorage.getItem('USERNAME'));
                else if(localStorage.getItem('USER_TYPE') == 'PHARMACIST')
                    this.pharmacistHomeRedirect();
                else if(localStorage.getItem('USER_TYPE') == 'DERMATOLOGIST')
                    this.dermatologistHomeRedirect();
                else
                    this.homeRedirect();
            }).
			catch((response) => 
            {
                alert("Wrong username/password");
            });
            
        },

        isSomeFieldEmpty: function(login)
		{
			if(login.username == '') return true;
			if(login.password == '') return true;
			return false;
		},

        parseJwt: function(token) {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));

            return JSON.parse(jsonPayload);
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
        pharmacyAdminRedirect(username){
            // treba poziv na back da vidim u kojoj apoteci radi
            this.$router
                .push({ name: "PharmacyView"})
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },
        pharmacistHomeRedirect()
        {
            this.$router
                .push({ name: "PharmacistHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },
        dermatologistHomeRedirect()
        {
            this.$router
                .push({ name: "DermatologistHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        }

    }


}

</script>

<style scoped>
input:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

</style>