<template>
    <div class="topnav" v-if="!isNavEnabled()">

        
        <!-- all -->
        <div v-if="!isUserType('DERMATOLOGIST') && !isUserType('PHARMACIST')">
            <a @click="homeRedirect()" class="homeNav"><i class="fa fa-home" style="font-size:24px"></i></a>
        </div>

        <!--unauthenticated user (guest) -->
        <div v-if="isUserType('GUEST')">
            <a @click="loginRedirect()" class="loginNav">Log in</a>
            <a @click="registerRedirect()" class="registerNav">Register</a>

        </div>

        <!--pharmacy admin-->
        <div v-if="isUserType('PHARMACY_ADMIN')">
            <a @click="adminSupplierRedirect()" class="registerNav">My profile</a>
            <a @click="pharmacyAdminReportRedirect()" class="registerNav">Pharmacy reports</a> 
        </div>

        <!--system admin-->
        <div v-if="isUserType('SYSTEM_ADMIN')">
            <a @click="adminSupplierRedirect()" class="registerNav">My profile</a>
            <a @click="adminOptionsRedirect()" class="registerNav">Options</a>
            <!-- <a @click="pharmacyRegisterRedirect()" class="registerNav">Pharmacy Registration</a> -->
            <!-- <a @click="pharmacyAdminRegistrationRedirect()" class="registerNav">Pharmacy Admin registration</a>
            <a @click="systemAdminRegistrationRedirect()" class="registerNav">System Admin Registration</a> -->
            <!-- <a @click="medicationRegistrationRedirect()" class="registerNav">Medication Registration</a> -->
            <!-- <a @click="supplierRegistrationRedirect()" class="registerNav">Supplier Registration</a>
            <a @click="dermatologistRegistrationRedirect()" class="registerNav">Dermos Registration</a>  -->
            <!-- <a @click="pharmacyComplaintRegistrationRedirect()" class="registerNav">Pharmacy Complaints</a> 
            <a @click="employeeComplaintRegistrationRedirect()" class="registerNav">Employee Complaints</a> -->
        </div>

        <!--supplier-->
        <div v-if="isUserType('SUPPLIER')">
            <a @click="adminSupplierRedirect()" class="registerNav">My profile</a>
            <a @click="medicationStorageRedirect()" class="registerNav">Medication Storage</a>
            <a @click="supplierOrdersRedirect()" class="registerNav">Pharmacy orders</a>
            <a @click="supplierOrdersOffersRedirect()" class="registerNav">Orders for my offers</a>
        </div>

        <!--patient-->
        <div v-if="isUserType('PATIENT')">
            <a @click="patientRedirect()" class="patientNav">Options</a>
            <a @click="patientProfileRedirect()" class="patientNav">My profile</a>
            <a @click="patientQrCodeRedirect()" class="patientNav">Upload qr code</a>

        </div>

        <!--dermatologist-->
        <div v-if="isUserType('DERMATOLOGIST')">
            <a @click="dermHomeRedirect()"><i class="fa fa-home" style="font-size:24px"></i></a>
            <a @click="dermProfileRedirect()">My Profile</a>
        </div>

        <!--pharmacist-->
        <div v-if="isUserType('PHARMACIST')">
            <a @click="pharmacistHomeRedirect()"><i class="fa fa-home" style="font-size:24px"></i></a>
            <a @click="dermProfileRedirect()">My Profile</a>
        </div>

        <!-- log out -->
        <a v-if="!isUserType('GUEST')"
            @click="logoutRedirect()"
            v-show="!isUserType('GUEST')"
            id="logoutNav"
            ><i class="fa fa-sign-out" aria-hidden="true" style="font-size:24px"></i></a
        >
    </div>
</template>

<script>
export default {
    name: "Navigation",

    data() {
        return {};
    },

    props: {
    },

    methods: {

        homeRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente
            if(localStorage.getItem("USER_TYPE") === "PHARMACY_ADMIN")
                this.$router
                .push({ name: "PharmacyView"})
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
            else
                this.$router.push({ name: "Home" }).catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        loginRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router.push({ name: "LoginPage" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        registerRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router
                .push({ name: "UserRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        pharmacyAdminRegisterRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router
                .push({ name: "PharmacyAdminRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        logoutRedirect: function () {
            //SIMULACIJA LOGOUTA!
            this.$store.commit("changeLoggedUserRole", "GUEST")
            this.$store.commit("changeLoggedUsername", "")

            localStorage.removeItem("USER_TOKEN");
            localStorage.removeItem("USER_TYPE");
            localStorage.removeItem("USER_EXPIRES")
            localStorage.removeItem("USERNAME");

            this.$router.push({ name: "Home" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        testLogin: function () {
            this.$store.commit("changeLoggedUserRole", "PHARMACIST")

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

        

        dermProfileRedirect: function () {
            this.$router.push({ name: "EmployeeProfilePage" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        dermHomeRedirect: function () {
            this.$router
                .push({ name: "DermatologistHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        isUserType: function (ut) {
            return this.$store.getters.getLoggedUserRole === ut;
        },

        isNavEnabled: function() {
            return this.$store.getters.getLogginFirstTimeDisableNav === true;
        },

        pharmacyRegisterRedirect: function () {
            this.$router
                .push({ name: "PharmacyRegistration" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        pharmacyAdminRegistrationRedirect: function () {
            this.$router
                .push({ name: "PharmacyAdminRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        systemAdminRegistrationRedirect: function () {
            this.$router
                .push({ name: "SystemAdminRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        supplierRegistrationRedirect: function () {
            this.$router
                .push({ name: "SupplierRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        dermatologistRegistrationRedirect: function() {
            this.$router
                .push({ name: "DermatologistRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        medicationRegistrationRedirect: function () {
            this.$router
                .push({ name: "MedicationRegistrationPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        medicationStorageRedirect: function () {
            this.$router
                .push({ name: "SupplierMedicationReserves" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        supplierOrdersRedirect: function () {
            this.$router
                .push({ name: "SupplierOrdersPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        supplierOrdersOffersRedirect: function () {
            this.$router
                .push({ name: "SupplierOrdersOffersPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        pharmacyComplaintRegistrationRedirect: function() {
            this.$router
                .push({ name: "AdminPharmacyComplaintPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        employeeComplaintRegistrationRedirect: function() {
            this.$router
                .push({ name: "AdminEmployeeComplaintPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        testPharmacyView: function(){
            this.$router
                .push({ name: "PharmacyView" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        patientRedirect: function(){
            this.$router
                .push({ name: "PatientHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },
        

        patientProfileRedirect: function(){
            this.$router
                .push({ name: "PatientProfile" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        patientQrCodeRedirect: function(){
            this.$router
                .push({ name: "QrCodeSearchPage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        pharmacistHomeRedirect: function(){
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
        pharmacyAdminReportRedirect : function(){
            this.$router
                .push({ name: "PharmacyAdminReportView" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        adminOptionsRedirect: function() {
            this.$router
                .push({ name: "AdminHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        adminSupplierRedirect: function() {
            this.$router
                .push({ name: "ProfileAdminSupplierPage" })
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.topnav {
    background-color: rgba(15, 95, 72, 1);
    overflow: hidden;
    width: 100%;
    position: fixed;
    top: 0;
    z-index: 999;
    height: 50px;

}

.topnav a {
    float: left;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 17px;
    z-index: 999;
    max-height: 100%;
}

.topnav a:hover {
    background-color: #ddd;
    color: rgba(10, 66, 50, 0.8);
}

.topnav a.active {
    background-color: #272327;
    color: white;
}

#logoutNav {
    float: right;
}

</style>
