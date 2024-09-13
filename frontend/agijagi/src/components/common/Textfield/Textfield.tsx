import React, { useRef, useState } from 'react';
import * as s from './Textfield.style';
import {
  TextfieldColor,
  TextfieldSize,
  ValidationState,
} from './Textfield.types';

interface TextfieldProps {
  label: string;
  inputValue: string;
  setInputValue: React.Dispatch<React.SetStateAction<string>>;
  size?: TextfieldSize;
  color?: TextfieldColor;
  fullWidth?: boolean;
  isColoredLabel?: boolean;
  helpText?: string;
  checkText?: string;
  warningText?: string;
  type?: string;
  disabled?: boolean;
  validationFunction?: (text: string) => ValidationState;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const defaultValidationFunction = (text: string): ValidationState => {
  return 'normal';
};

const Textfield = ({
  label,
  size = 'md',
  color = 'primary',
  fullWidth = false,
  isColoredLabel = false,
  helpText = '',
  checkText = '',
  warningText = '',
  inputValue = '',
  type = '',
  disabled = false,
  setInputValue,
  validationFunction = defaultValidationFunction,
  onChange,
}: TextfieldProps) => {
  const [focused, setFocused] = useState(false);
  const [isFloating, setIsFloating] = useState<boolean>(false);
  const [fieldState, setFieldState] = useState<ValidationState>('normal');
  const inputRef = useRef<HTMLInputElement>(null);

  const handleFocus = () => {
    setFocused(true);
    setIsFloating(true);
  };

  const hadleblur = () => {
    setFocused(false);
    if (inputRef.current?.value === '') setIsFloating(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsFloating(() => focused || e.target.value !== '');
    setInputValue(e.target.value);
    setFieldState(validationFunction(e.target.value));
    if (onChange) onChange(e);
  };

  const bottomText = () => {
    switch (fieldState) {
      case 'danger':
        return warningText;
      case 'success':
        return checkText;
      case 'normal':
        return helpText;
    }
  };

  return (
    <>
      <s.TextfieldContainer
        size={size}
        fieldState={fieldState}
        color={color}
        disable={disabled}
        fullWidth={fullWidth}
      >
        <s.Label
          isFloating={isFloating}
          size={size}
          color={color}
          fieldState={fieldState}
          disable={disabled}
          isColoredLabel={isColoredLabel}
        >
          {label}
        </s.Label>
        <s.Textfield
          type={type}
          value={inputValue}
          isColoredLabel={isColoredLabel}
          color={color}
          fieldSize={size}
          fieldState={fieldState}
          disabled={disabled}
          ref={inputRef}
          onFocus={handleFocus}
          onBlur={hadleblur}
          onChange={handleChange}
        />
      </s.TextfieldContainer>
      {!disabled && (
        <s.StateText size={size} fieldState={fieldState}>
          {bottomText()}
        </s.StateText>
      )}
    </>
  );
};

export default Textfield;
