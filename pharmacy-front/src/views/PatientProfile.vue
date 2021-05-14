<template>
    <div id="profileDiv">
        <p class="titl">My profile</p>
        <div id="flexDiv" v-if="user">
            <div id="sideBar" class="card flex-card">
                <div id="pictureDiv">
                    <img src="../assets/profilePicture.png" />
                </div>
                <div id="userInfoDiv">
                    <p><b>Username: </b>{{ user.username }}</p>
                    <p><b>Name: </b>{{ user.firstName }} {{ user.lastName }}</p>
                    <p><b>Email: </b>{{ user.email }}</p>
                    <p>
                        <b>Address: </b>{{ user.location.street }}
                        {{ user.location.streetNum }}, {{ user.location.city }}
                    </p>
                </div>
            </div>
            <div id="optionsBar" class="card flex-card">
                <TabNav
                    :tabs="[
                        'Edit profile',
                        'Change password',
                        'Allergies',
                        'Subscriptions',
                        'Penalty system',
                    ]"
                    :selected="selected"
                    @selected="setSelected"
                >
                    <Tab :isSelected="selected === 'Edit profile'">
                        <PatientProfileEdit></PatientProfileEdit>
                    </Tab>

                    <Tab :isSelected="selected === 'Change password'">
                        <PasswordChangeComponent></PasswordChangeComponent>
                    </Tab>

                    <Tab :isSelected="selected === 'Allergies'">
                        <AllergiesAddEdit></AllergiesAddEdit>
                    </Tab>
                </TabNav>
            </div>
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import moment from "moment";
import Button from "@/components/Button";
import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";
import AllergiesAddEdit from "../components/AllergiesAddEdit.vue";
import PatientProfileEdit from "../components/PatientProfileEdit.vue";
import PasswordChangeComponent from "../components/PasswordChangeComponent.vue";

import $ from "jquery";

export default {
    name: "PatientProfile",

    components: { Button, TabNav, Tab, AllergiesAddEdit, PatientProfileEdit, PasswordChangeComponent },

    data() {
        return {
            user: null,
            selected: "Edit profile",
        };
    },

    methods: {
        setSelected(tab) {
            this.selected = tab;
        },
    },

    mounted() {
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => (this.user = response.data));
    },
};
</script>


<style scoped>
#profileDiv {
    margin: 50px 50px 50px 50px;
}

.titl {
    font-size: 30px;
}

.flex-card {
    margin: 15px;
    border-color: rgba(142, 141, 141, 0.694);
    /* width: 100%; */
    height: 100%;
    min-height: 50vh;
}
#flexDiv {
    display: flex;
}

#sideBar {
    flex: 1;
    display: flex;
    padding: 40px;
}

#pictureDiv {
    align-content: center;
}

img {
    max-width: 50%;
    align-content: center;
    margin-bottom: 50px;
}

#userInfoDiv {
    text-align: left;
}

#optionsBar {
    flex: 3;
}
</style>

