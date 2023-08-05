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




