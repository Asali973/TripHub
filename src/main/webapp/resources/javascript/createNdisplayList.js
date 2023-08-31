function toggleCreateForm() {
    // Get the create form container and the hidden message by their IDs
    const createFormContainer = document.getElementById('createFormContainer');
    const hiddenMessage = document.getElementById('hiddenMessage');

    // Toggle the visibility of the create form container and the hidden message using 'display' 
    if (createFormContainer.style.display === 'none' || createFormContainer.style.display === '') {
        createFormContainer.style.display = 'block'; // Show the create form
        hiddenMessage.style.display = 'none'; // Hide the message
    } else {
        createFormContainer.style.display = 'none'; // Hide the create form
        hiddenMessage.style.display = 'block'; // Show the message
    }
}

function toggleTableVisibility() {
    const dataTableContainer = document.getElementById('dataTableContainer');
    if (dataTableContainer.style.display === 'none' || dataTableContainer.style.display === '') {
        dataTableContainer.style.display = 'block';
    } else {
        dataTableContainer.style.display = 'none';
    }
}







    function confirmDelete() {
        var result = confirm("Are you sure that you want to delete this item?");
        return result;
    }

    function showDeleteSuccessMessage() {
        alert("Deletion of tour package successful.");
    }

    function confirmDeleteAccom() {
        var result = confirm("Are you sure that you want to delete this item?");
        return result;
    }

    function showDeleteSuccessMessage() {
        alert("Deletion of Accommodation successful.");
    }

