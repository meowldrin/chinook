import React from 'react/addons';
import Formsy from 'formsy-react';

const TextInputComponent = React.createClass({

  // Add the Formsy Mixin
  mixins: [Formsy.Mixin],

  // setValue() will set the value of the component, which in
  // turn will validate it and the rest of the form
  changeValue(event) {
    this.setValue(event.currentTarget.value);
  },


  render() {
    // Set a specific className based on the validation
    // state of this component. showRequired() is true
    // when the value is empty and the required prop is
    // passed to the input. showError() is true when the
    // value typed is invalid
    const className = this.showRequired() ? 'required' : this.showError() ? 'error' : null;

    // An error message is returned ONLY if the component is invalid
    // or the server has returned an error message
    const errorMessage = this.getErrorMessage();

    return (
      <div className="col-sm-8 margin_bottom">
        <input type="text" className="form-control"
               onChange={this.changeValue} value={this.getValue()} placeholder={this.props.placeholder}/>
        <span>{errorMessage}</span>
      </div>
    );
  }
});

export default TextInputComponent
