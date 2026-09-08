export interface SelectOption {
  value: string;
  label: string;
}

interface BaseSelectProps {
  value: string;
  options: SelectOption[];
  disabled?: boolean;
  className?: string;
  onChange: (value: string) => void;
}

export const BaseSelect = ({
  value,
  options,
  disabled,
  className = "",
  onChange,
}: BaseSelectProps) => {
  return (
    <select
      className={`select select-bordered ${className}`}
      value={value}
      disabled={disabled}
      onChange={(e) => {
        e.target.blur();

        const newValue = e.target.value;

        setTimeout(() => {
          onChange(newValue);
        }, 10);
      }}
    >
      {options.map((option) => (
        <option key={option.value} value={option.value}>
          {option.label}
        </option>
      ))}
    </select>
  );
};
