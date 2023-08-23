function toggleCreateForm() {
    // Get the create form container by its ID
    const createFormContainer = document.getElementById('createFormContainer');

    // Toggle the visibility of the create form container using  'display' 
    if (createFormContainer.style.display === 'none') {
        createFormContainer.style.display = 'block'; // Show the create form
    } else {
        createFormContainer.style.display = 'none'; // Hide the create form
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

