const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {ingredients: []};
    }

    componentDidMount() {
        fetch("/ingredients")
            .then(function(response) {
                return response.json();
            })
            .then(result => {
                    console.log(result);
                    this.setState({
                        ingredients: result._embedded.ingredientList
                    });
                    console.log(this.state.ingredients);
                },
                (error) => {
                    console.log(error);
                });
    }

    render() {
        return (
            <IngredientList ingredients={this.state.ingredients}/>
        )
    }
}

class IngredientList extends React.Component {
    render() {

        console.log(this.props.ingredients);
        const ingredients = this.props.ingredients.map(ingredient =>
            <Ingredient key={ingredient._links.self.href} ingredient={ingredient}/>
        );
        return (
            <table>
                <thead>
                    <tr>
                        <th>
                            Ingredients
                        </th>
                    </tr>
                </thead>

                <tbody>
                {ingredients}
                </tbody>
            </table>
        );

    }
}

class Ingredient extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.ingredient.name}</td>
            </tr>
        );
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
);