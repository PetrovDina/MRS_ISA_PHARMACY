<template>
    <div id="employees">
        <label v-if="employees.length == 0"><span style="font-size: 20px;">There are no employees that you can write complaint on.</span></label>
        <div v-if="employees.length != 0">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Work as</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Rating</th>
                    </tr>
                </thead>

                <tbody>
                    <tr
                        :key="employee.id"
                        v-for="employee in employees"
                        @click="chooseEmployee(employee)"
                        :class="[
                                selected === employee
                                ? 'selected'
                                : '',
                        ]"
                    >
                        <td>{{employee.username}}</td>
                        <td>{{employee.email}}</td>
                        <td>{{employee.firstName}}</td>
                        <td>{{employee.lastName}}</td>
                        <td>{{employee.employeeType}}</td>
                        <td><i v-bind:class="employee.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td> 
                        <td>{{employee.rating}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
export default {

    name: "EmployeeTable",
    
    props: {
        employees: {
            type: Array,
            required: false,
        },
    },

    data() {
        return {
            selected: {},
        };
    },

    methods: {

        chooseEmployee(employee)
        {
            this.selected = employee;
            this.$emit("employeeSelected", this.selected);
        },

    },
};
</script>

<style scoped>
#employees {
    margin: 30px 100px 30px 100px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.selected {
    background-color: rgba(155, 82, 151, 0.527);
}
</style>
