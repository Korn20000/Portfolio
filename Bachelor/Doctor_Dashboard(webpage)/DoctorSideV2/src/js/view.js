function view() {
    "use strict";

    var table = null;

    function buildPatientList(patients) {
        console.log('view patients:', patients);
        if (patients === null) {
            alert("Error receiving data !");
        } else {
            document.getElementById("main_table").appendChild = getDataTable(patients);
        }
    }

    function hideMainTable() {
        document.getElementById("main_table").style.display = 'none';
    }

    function displayMainTable() {
        document.getElementById("main_table").style.display = 'block';
    }

    function hideLoading() {
        document.getElementById("loadingList").style.display = 'none';
    }

    function displayLoading() {
        document.getElementById("loadingList").style.display = 'block';
    }

    function getDataTable(patients) {
        table = $('#patientsTable').DataTable({
            data: patients,
            columns: [{
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },
                {
                    "data": "CPR",
                    "title": "CPR"
                },
                {
                    "data": "First_Name",
                    "title": "First Name"
                },
                {
                    "data": "Last_Name",
                    "title": "Last Name"
                },
                {
                    "data": "Age",
                    "title": "Age"
                },
                {
                    "data": "Diabetes_Type",
                    "title": "Diabetes Type"
                },
                {
                    "data": "Phone",
                    "title": "Phone Number"
                },
                {
                    "data": "Address",
                    "title": "Address"
                },
                {
                    "data": "Email",
                    "title": "E-mail"
                }
            ],
            order: [
                [1, 'asc']
            ],
            responsive: true,
            dom: 'Bfrtip',
            stateSave: true,
            lengthMenu: [
                [10, 25, 50, -1],
                ['10 rows', '25 rows', '50 rows', 'Show all']
            ],
            columnDefs: [{
                targets: 1,
                className: 'noVis'
            }, {
                targets: 0,
                className: 'noVis'
            }],
            buttons: ['pageLength', {
                    extend: 'colvis',
                    columns: ':not(.noVis)'
                },
                {
                    extend: 'copyHtml5',
                    exportOptions: {
                        columns: [0, ':visible']
                    }
                },
                {
                    extend: 'excelHtml5',
                    exportOptions: {
                        columns: ':visible'
                    }
                },
                {
                    extend: 'pdfHtml5',
                    exportOptions: {
                        columns: [0, 1, 2, 5]
                    }
                },
                {
                    extend: 'print',
                    exportOptions: {
                        columns: ':visible'
                    }
                }
            ]
        });

        new $.fn.dataTable.FixedHeader(table);

        console.log("tableIs", table);
        return table;
    }

    function getTable() {
        return table;
    }

    return {
        buildPatientList,
        hideMainTable,
        displayMainTable,
        hideLoading,
        displayLoading,
        getTable
    };
}