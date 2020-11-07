$(document).ready(() => {

    const container = $("#mynetwork")[0];
    const options = {
        edges: {
            arrows: {
                to: {
                    enabled: true
                }
            }
        },
        physics: {
            enabled: true,
            stabilization: {
                enabled: true,
                iterations: 10000,
                updateInterval: 100,
            },
        },
        interaction: {
            dragNodes: false,
        }
    }

    $('#process').on('click', _makeAjaxCall)

    function _makeAjaxCall() {
        $.ajax({
            url: "/process",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                url: $("#url")[0].value,
                maxPages: $("#max_requests")[0].value,
            }),
        })
            .done(_drawGraph)
            .fail(message => alert(JSON.stringify(message, null, 2)));
    }

    function _drawGraph(rawData) {
        const data = {
            nodes: new vis.DataSet(rawData.nodes),
            edges: new vis.DataSet(rawData.edges)
        }
        const network = new vis.Network(container, data, options);
        network.physics.hidden = true;
        setTimeout(() => network.physics.stopSimulation(), 10000)
        alert("finished");
    }
})