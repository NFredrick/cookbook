const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            recipes: [],
            ingredients: []
        };
        this.viewRecipe = this.viewRecipe.bind(this);
    }

    componentDidMount() {

        fetch("/recipes")
            .then(function (response) {
                return response.json();
            })
            .then(result => {
                this.setState({
                    recipes: result._embedded.recipeList
                });
            },
            (error) => {
                console.log(error);
            });
    }

    viewRecipe = (recipeContents) => {
        console.log(recipeContents);
        this.setState({
            ingredients: recipeContents
        });
    };

    divStyle = {
        width: "100%"
    };

    divLeft = {
        float: "left",
        width: "30%"
    };

    divRight = {
        float: "right",
        width: "70%"
    };

    render() {
        return (
            <div style={this.divStyle}>
                <div style={this.divLeft}>
                    <RecipeList recipes={this.state.recipes} viewRecipe={this.viewRecipe}/>
                </div>
                <div style={this.divRight}>
                    <IngredientList ingredients={this.state.ingredients}/>
                </div>
            </div>
        )
    }
}

class RecipeList extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const recipes = this.props.recipes.map(recipe =>
            <Recipe key={recipe._links.self.href} recipe={recipe} viewRecipe={this.props.viewRecipe}/>
        );
        return (
            <table border="1">
                <thead>
                <tr>
                    <th>
                        Recipes
                    </th>
                </tr>
                </thead>

                <tbody>
                {recipes}
                </tbody>
            </table>
        );
    }
}

class Recipe extends React.Component {
    constructor(props) {
        super(props);
    }

    selectRecipe = () => {
        this.props.viewRecipe(this.props.recipe.ingredients)
    };

    render() {
        return (
            <tr>
                <td>
                    {this.props.recipe.name}
                </td>
                <td><button onClick={this.selectRecipe}>View</button></td>
            </tr>
        )
    }
}

class IngredientList extends React.Component {

    render() {
        console.log(this.props.ingredients);
        if (this.props.ingredients) {
            const ingredients = this.props.ingredients.map(ingredient =>
                <Ingredient key={ingredient.id} ingredient={ingredient}/>
            );
            return (
                <table border="1">
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
        } else {
            return;
        }
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