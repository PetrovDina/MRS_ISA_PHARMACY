<template>
    <div id="patientsDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Appointment Date</th>

                </tr>
            </thead>

            <tbody>
                <tr :key="p.id" v-for="p in patients">
                    <td>{{p.id}}</td>
                    <td>{{p.name }}</td>
                    <td>{{p.lastName}}</td> 
                    <td>{{p.date}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "SearchPatientsPage",

    data() {
        return {
            patients: [
                {
                    id: 1,
                    name: "John",
                    lastName: "Smith",
                    date: "12.12.2020."
                },
                {
                    id: 2,
                    name: "Jane",
                    lastName: "Doe",
                    date: "11.11.2020."
                },
                {
                    id: 3,
                    name: "Mike",
                    lastName: "Anderson",
                    date: "10.10.2020."
                },
            ],
        };
    },

    mounted() {

    },

    methods: {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#patientsDiv{
    margin: 30px 60px 30px 60px;
}


thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>

