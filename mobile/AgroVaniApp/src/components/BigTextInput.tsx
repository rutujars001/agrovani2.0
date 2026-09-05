import React from 'react';
import {StyleSheet, Text, TextInput, View} from 'react-native';

import {colors, fontSize, radius, spacing, touchTarget} from '../theme/theme';

type Props = {
  label: string;
  value: string;
  onChangeText: (text: string) => void;
  placeholder?: string;
  error?: string;
  keyboardType?: 'default' | 'phone-pad';
  maxLength?: number;
};

export default function BigTextInput({
  label,
  value,
  onChangeText,
  placeholder,
  error,
  keyboardType = 'default',
  maxLength,
}: Props): React.JSX.Element {
  return (
    <View style={styles.wrapper}>
      <Text style={styles.label}>{label}</Text>
      <TextInput
        value={value}
        onChangeText={onChangeText}
        placeholder={placeholder}
        placeholderTextColor={colors.textLight}
        keyboardType={keyboardType}
        maxLength={maxLength}
        accessibilityLabel={label}
        style={[styles.input, !!error && styles.inputError]}
      />
      {!!error && <Text style={styles.error}>{error}</Text>}
    </View>
  );
}

const styles = StyleSheet.create({
  wrapper: {
    marginBottom: spacing.lg,
  },
  label: {
    fontSize: fontSize.body,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.sm,
  },
  input: {
    minHeight: touchTarget.min,
    borderWidth: 2,
    borderColor: colors.border,
    borderRadius: radius.md,
    paddingHorizontal: spacing.md,
    fontSize: fontSize.body,
    color: colors.text,
    backgroundColor: colors.surface,
  },
  inputError: {
    borderColor: colors.error,
  },
  error: {
    fontSize: fontSize.small,
    color: colors.error,
    marginTop: spacing.xs,
  },
});