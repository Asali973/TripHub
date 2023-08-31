
function toggleAccommodationSearch() {
	togglePanel('accommodationForm:accommodationPanelGroup');
}

function toggleTransportationSearch() {
	togglePanel('transportationForm:transportationPanelGroup');
}

function toggleRestaurantSearch() {
	togglePanel('restaurantForm:restaurantPanelGroup');
}

function togglePanel(panelId) {
	var panel = document.getElementById(panelId);
	if (panel.style.display === 'none') {
		panel.style.display = 'block';
	} else {
		panel.style.display = 'none';
	}
}
function toggleResultSection(panelId) {
	var resultPanel = document.getElementById(panelId);
	if (resultPanel.style.display === 'none') {
		resultPanel.style.display = 'block';
	} else {
		resultPanel.style.display = 'none';
	}
}
