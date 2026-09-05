import React, {useState} from 'react';
import {
  Alert,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import {SafeAreaView} from 'react-native-safe-area-context';
import type {NativeStackScreenProps} from '@react-navigation/native-stack';

import BigButton from '../components/BigButton';
import BigTextInput from '../components/BigTextInput';
import {t} from '../i18n/strings';
import type {RootStackParamList} from '../navigation/types';
import {ApiError} from '../services/apiClient';
import {farmerService} from '../services/farmerService';
import {storage} from '../services/storage';
import {colors, fontSize, spacing} from '../theme/theme';

type Props = NativeStackScreenProps<RootStackParamList, 'Registration'>;

export default function RegistrationScreen({navigation}: Props): React.JSX.Element {
  const [fullName, setFullName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [village, setVillage] = useState('');
  const [taluka, setTaluka] = useState('');
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [submitting, setSubmitting] = useState(false);

  const validate = (): boolean => {
    const next: Record<string, string> = {};

    if (!fullName.trim()) {
      next.fullName = t.register.errorName;
    }
    if (!/^[6-9]\d{9}$/.test(phoneNumber.trim())) {
      next.phoneNumber = t.register.errorPhone;
    }

    setErrors(next);
    return Object.keys(next).length === 0;
  };

  const handleSubmit = async () => {
    if (!validate()) {
      return;
    }

    setSubmitting(true);
    setErrors({});

    try {
      const farmer = await farmerService.register({
        fullName: fullName.trim(),
        phoneNumber: phoneNumber.trim(),
        village: village.trim() || undefined,
        taluka: taluka.trim() || undefined,
        district: 'Solapur',
        preferredLanguage: 'mr',
      });

      await storage.saveFarmer(farmer);
      await storage.setOnboarded();

      navigation.reset({index: 0, routes: [{name: 'Home'}]});
    } catch (err) {
      if (err instanceof ApiError) {
        if (err.fieldErrors) {
          setErrors(err.fieldErrors);
        } else {
          Alert.alert(t.appName, err.message);
        }
      } else {
        Alert.alert(t.appName, t.errors.unknown);
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <SafeAreaView style={styles.safeArea} edges={['top', 'bottom']}>
      <KeyboardAvoidingView
        style={styles.flex}
        behavior={Platform.OS === 'ios' ? 'padding' : undefined}>
        <ScrollView
          contentContainerStyle={styles.content}
          keyboardShouldPersistTaps="handled">
          <View style={styles.header}>
            <Text style={styles.appName}>{t.appName}</Text>
            <Text style={styles.title}>{t.register.title}</Text>
            <Text style={styles.subtitle}>{t.register.subtitle}</Text>
          </View>

          <BigTextInput
            label={t.register.fullName}
            value={fullName}
            onChangeText={setFullName}
            placeholder={t.register.fullNameHint}
            error={errors.fullName}
            maxLength={120}
          />

          <BigTextInput
            label={t.register.phone}
            value={phoneNumber}
            onChangeText={text => setPhoneNumber(text.replace(/\D/g, ''))}
            placeholder={t.register.phoneHint}
            error={errors.phoneNumber}
            keyboardType="phone-pad"
            maxLength={10}
          />

          <BigTextInput
            label={t.register.village}
            value={village}
            onChangeText={setVillage}
            placeholder={t.register.villageHint}
            maxLength={100}
          />

          <BigTextInput
            label={t.register.taluka}
            value={taluka}
            onChangeText={setTaluka}
            placeholder={t.register.talukaHint}
            maxLength={100}
          />

          <BigButton
            label={t.register.submit}
            onPress={handleSubmit}
            loading={submitting}
            style={styles.submit}
          />
        </ScrollView>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.background,
  },
  flex: {
    flex: 1,
  },
  content: {
    padding: spacing.lg,
    paddingBottom: spacing.xxl,
  },
  header: {
    alignItems: 'center',
    marginBottom: spacing.xl,
  },
  appName: {
    fontSize: fontSize.title,
    fontWeight: '700',
    color: colors.primary,
  },
  title: {
    fontSize: fontSize.large,
    fontWeight: '600',
    color: colors.text,
    marginTop: spacing.md,
  },
  subtitle: {
    fontSize: fontSize.body,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  submit: {
    marginTop: spacing.md,
  },
});