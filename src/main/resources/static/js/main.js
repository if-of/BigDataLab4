$(document).ready(() => {

    $('#process').on('click', _makeAjaxCall)

    const options = {
        edges: {
            arrows: {
                to: {
                    enabled: true
                }
            }
        }
    }

    function _makeAjaxCall() {
        // create an array with nodes
        let nodes = new vis.DataSet([
            {id: 1, label: "Node 1", title: "igor"},
            {id: 2, label: "Node 2"},
            {id: 3, label: "Node 3"},
            {id: 4, label: "Node 4"},
            {id: 5, label: "Node 5"},
        ]);

        // create an array with edges
        let edges = new vis.DataSet([
            {from: 1, to: 3},
            {from: 1, to: 2},
            {from: 2, to: 1},
            {from: 2, to: 4},
            {from: 2, to: 5},
            {from: 3, to: 3},
        ]);

        // create a network
        const container = $("#mynetwork")[0];
        const data = {
            nodes: nodes,
            edges: edges,
        };

        const network = new vis.Network(container, data, options);
    }
})