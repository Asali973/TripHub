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

document.addEventListener('DOMContentLoaded', function() {
    // Get the "Add Package" form and button
    const addTourForm = document.getElementById('addTourForm');
    const addPackageButton = addTourForm.querySelector('button[type="submit"]');

    // Add a submit event listener to the "Add Package" form
    addTourForm.addEventListener('submit', function(event) {
        event.preventDefault();
        // Call  function to submit the form data to the server (if needed)
        submitAddTourForm();
    });

    // Get the "Display the list" form and button
    const displayListForm = document.getElementById('displayListForm');
    const displayListButton = displayListForm.getElementById('displayListButton');

    // Add a click event listener to the "Display the list" button
    displayListButton.addEventListener('click', function() {
        // Use AJAX to fetch the updated list data
        fetchUpdatedListData();
    });
});



