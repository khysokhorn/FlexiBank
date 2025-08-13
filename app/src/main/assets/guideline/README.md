# FlexiBank Android Development Guidelines

This directory contains the official development guidelines and standards for the FlexiBank Android application.

## Purpose

These guidelines serve as a reference for maintaining consistent development practices across the application. They are accessible both through this documentation and programmatically via the `GuidelineManager` and `GuidelineProvider` classes.

## How to Use

1. **In code editor**: Browse these markdown files directly
2. **In app**: Access via the GuidelineManager API
3. **For AI assistants**: The guidelines are made available to AI systems to ensure consistent advice

## Available Guidelines

- `DevelopmentGuidelines.md`: Core development standards and practices
- `CodingStyleGuide.md`: Code formatting and style examples
- `ProjectStructure.md`: Package organization and architecture
- `BestPractices.md`: Android development best practices

## Updating Guidelines

When updating these guidelines:

1. Modify the appropriate markdown files
2. Consider updating the code examples in `CodingStyleGuide.md` to reflect current best practices
3. Ensure the `GuidelineManager.GuidelineDocument` enum is updated if adding new files

## Integration with AI Assistant

The guidelines in this directory are made available to the AI assistant through the `GuidelineProvider` class. This ensures that all code suggestions and recommendations follow the project's established standards.

The AI assistant can access these guidelines to provide code examples, architectural advice, and best practices that are consistent with the project's requirements.
